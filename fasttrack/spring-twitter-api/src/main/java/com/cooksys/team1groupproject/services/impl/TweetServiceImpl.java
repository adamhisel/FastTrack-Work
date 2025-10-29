package com.cooksys.team1groupproject.services.impl;

import com.cooksys.team1groupproject.dtos.*;
import com.cooksys.team1groupproject.entities.Credentials;
import com.cooksys.team1groupproject.entities.Hashtag;
import com.cooksys.team1groupproject.entities.Tweet;
import com.cooksys.team1groupproject.entities.User;
import com.cooksys.team1groupproject.exceptions.BadRequestException;
import com.cooksys.team1groupproject.exceptions.NotAuthorizedException;
import com.cooksys.team1groupproject.exceptions.NotFoundException;
import com.cooksys.team1groupproject.mappers.CredentialsMapper;
import com.cooksys.team1groupproject.mappers.HashtagMapper;
import com.cooksys.team1groupproject.mappers.TweetMapper;
import com.cooksys.team1groupproject.mappers.UserMapper;
import com.cooksys.team1groupproject.repositories.HashtagRepository;
import com.cooksys.team1groupproject.repositories.TweetRepository;
import com.cooksys.team1groupproject.repositories.UserRepository;
import com.cooksys.team1groupproject.services.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {

    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;
    private final CredentialsMapper credentialsMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HashtagRepository hashtagRepository;
    private final HashtagMapper hashtagMapper;

    private User validateCredentials(CredentialsDto credentialsDto) {
        Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
        if (credentials == null || credentials.getPassword() == null || credentials.getUsername()== null){
            throw new BadRequestException("Credentials are required");
        }

        Optional<User> existingUser = userRepository.findByCredentialsUsernameAndDeletedFalse(credentials.getUsername());
        if (existingUser.isEmpty()) {
            throw new NotFoundException("No user found with that username");
        }
        User user = existingUser.get();
        if (!user.getCredentials().getUsername().equals(credentials.getUsername()) || !user.getCredentials().getPassword().equals(credentials.getPassword())){
            throw new NotAuthorizedException("Username or password is incorrect");
        }

        return user;
    }

    private Tweet getTweet(Long id) {
        Optional<Tweet> tweetOptional = tweetRepository.findByIdAndDeletedFalse(id);

        if (tweetOptional.isEmpty()) {
            throw new NotFoundException(String.format("No tweet found with id: %d", id));
        }

        return tweetOptional.get();
    }

    private Tweet getSecureTweet(Long id, CredentialsDto credentialsDto) {
        Tweet tweet = getTweet(id);
        User user = validateCredentials(credentialsDto);

        if (!tweet.getAuthor().equals(user)) {
            throw new NotAuthorizedException("You are not authorized to edit this tweet.");
        }

        return tweet;
    }

    // GET tweets
    @Override
    public List<TweetResponseDto> getTweets() {
        return tweetMapper.entitiesToDtos(tweetRepository.findAllByDeletedFalse());
    }

    // POST tweets
    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
        return tweetMapper.entityToDto(createTweetWrapper(tweetRequestDto));
    }

    private Tweet createTweetWrapper(TweetRequestDto tweetRequestDto) {
        if (tweetRequestDto == null || tweetRequestDto.getContent() == null || tweetRequestDto.getContent().trim().isEmpty()) {
            throw new BadRequestException("Tweet content is required");
        }

        User user = validateCredentials(tweetRequestDto.getCredentials());
        Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);

        tweet.setAuthor(user);
        tweet = tweetRepository.saveAndFlush(tweet);

        processContent(tweet);
        tweet = tweetRepository.saveAndFlush(tweet);

        return tweet;
    }

    private void processContent(Tweet tweet) {
        String content = tweet.getContent();
        String[] words = content.split("\\s+");

        for (String word : words) {
            if (word.startsWith("@") && word.length() > 1) {
                String username = word.substring(1).replaceAll("[^a-zA-Z0-9_]", "");
                if (!username.isEmpty()) {
                    Optional<User> mentionedUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
                    if (mentionedUser.isPresent()) {
                        User user = mentionedUser.get();
                        user.getMentions().add(tweet);
                        userRepository.saveAndFlush(user);
                    }
                }
            } else if (word.startsWith("#") && word.length() > 1) {
                String tagLabel = word.substring(1).replaceAll("[^a-zA-Z0-9_]", "");
                if (!tagLabel.isEmpty()) {
                    Hashtag hashtag = findOrCreateHashtag(tagLabel);
                    tweet.getHashtags().add(hashtag);
                }
            }
        }
    }

    private Hashtag findOrCreateHashtag(String label) {
        Optional<Hashtag> existingHashtag = hashtagRepository.findByLabel(label);

        if (existingHashtag.isPresent()) {
            Hashtag hashtag = existingHashtag.get();
            hashtag.setLastUsed(Timestamp.from(Instant.now()));
            return hashtagRepository.saveAndFlush(hashtag);
        } else {
            Hashtag newHashtag = new Hashtag();
            newHashtag.setLabel(label);
            newHashtag.setFirstUsed(Timestamp.from(Instant.now()));
            newHashtag.setLastUsed(Timestamp.from(Instant.now()));
            return hashtagRepository.saveAndFlush(newHashtag);
        }
    }

    // GET tweets/{id}
    @Override
    public TweetResponseDto getTweetById(Long id) {
        return tweetMapper.entityToDto(getTweet(id));
    }

    // DELETE tweets/{id}
    @Override
    public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {
        Tweet tweet = getSecureTweet(id, credentialsDto);
        tweet.setDeleted(true);
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }

    // POST tweets/{id}/like
    @Override
    public void createLike(Long id, CredentialsDto credentialsDto) {
        User user = validateCredentials(credentialsDto);
        Tweet tweet = getTweet(id);

        user.getLikedTweets().add(tweet);
        tweet.getLikedBy().add(user);

        userRepository.saveAndFlush(user);
        tweetRepository.saveAndFlush(tweet);
    }

    // POST tweets/{id}/reply
    @Override
    public TweetResponseDto createReply(Long id, TweetRequestDto tweetRequestDto) {
        Tweet originalTweet = getTweet(id);
        Tweet tweet = createTweetWrapper(tweetRequestDto);

        tweet.setInReplyTo(originalTweet);
        tweet = tweetRepository.saveAndFlush(tweet);

        return tweetMapper.entityToDto(tweet);
    }

    // POST tweets/{id}/repost
    @Override
    public TweetResponseDto createRepost(Long id, CredentialsDto credentialsDto) {
        User user = validateCredentials(credentialsDto);
        Tweet originalTweet = getTweet(id);

        Tweet tweet = new Tweet();
        tweet.setAuthor(user);
        tweet.setRepostOf(originalTweet);

        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }

    // GET tweets/{id}/tags
    @Override
    public List<HashtagDto> getTags(Long id) {
        Tweet tweet = getTweet(id);
        return hashtagMapper.entitiesToDtos(new ArrayList<>(tweet.getHashtags()));
    }

    // GET tweets/{id}/likes
    @Override
    public List<UserResponseDto> getLikes(Long id) {
        Tweet tweet = getTweet(id);
        return userMapper.entitiesToDtos(new ArrayList<>(tweet.getLikedBy()));
    }

    // GET tweets/{id}/context
    @Override
    public ContextDto getContext(Long id) {
        Tweet tweet = getTweet(id);

        ContextDto contextDto = new ContextDto();
        contextDto.setTarget(tweetMapper.entityToDto(tweet));
        contextDto.setBefore(getBeforeChain(tweet));
        contextDto.setAfter(getAfterChain(tweet));

        return contextDto;
    }

    private List<TweetResponseDto> getBeforeChain(Tweet tweet) {
        List<TweetResponseDto> beforeChain = new ArrayList<>();
        Tweet current = tweet.getInReplyTo();

        while (current != null && !current.isDeleted()) {
            beforeChain.add(0, tweetMapper.entityToDto(current));
            current = current.getInReplyTo();
        }

        return beforeChain;
    }

    private List<TweetResponseDto> getAfterChain(Tweet tweet) {
        List<TweetResponseDto> afterChain = new ArrayList<>();
        collectAllReplies(tweet, afterChain);

        return afterChain;
    }

    private void collectAllReplies(Tweet tweet, List<TweetResponseDto> allReplies) {
        for (Tweet reply : tweet.getReplies()) {
            if (!reply.isDeleted()) {
                allReplies.add(tweetMapper.entityToDto(reply));
                collectAllReplies(reply, allReplies);
            }
        }
    }

    // GET tweets/{id}/replies
    @Override
    public List<TweetResponseDto> getReplies(Long id) {
        Tweet tweet = getTweet(id);
        return tweetMapper.entitiesToDtos(tweet.getReplies());
    }

    // GET tweets/{id}/reposts
    @Override
    public List<TweetResponseDto> getReposts(Long id) {
        Tweet tweet = getTweet(id);
        return tweetMapper.entitiesToDtos(tweet.getReposts());
    }

    // GET tweets/{id}/mentions
    @Override
    public List<UserResponseDto> getMentions(Long id) {
        Tweet tweet = getTweet(id);
        return userMapper.entitiesToDtos(new ArrayList<>(tweet.getMentionedBy()));
    }

}
