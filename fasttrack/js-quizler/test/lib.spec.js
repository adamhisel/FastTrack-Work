import jsv from 'jsverify'
import _ from 'underscore'

import { chooseRandom, createPrompt, createQuestions } from '../src/lib.js'

describe('chooseRandom', () => {
  const testArray = ['apple', 'banana', 'cherry', 'date', 'elderberry']

  it('returns an empty array when given an empty array', () => {
    jsv.assert(
      jsv.forall(jsv.integer, (numItems) => {
        return chooseRandom()([], numItems).length === 0
      })
    )
  })

  it('returns a random selection with the specified number of items', () => {
    jsv.assert(
      jsv.forall(jsv.integer(1, testArray.length), (numItems) => {
        const result = chooseRandom()(testArray, numItems)
        return result.length === numItems && result.every((item) => testArray.includes(item))
      })
    )
  })

  it('returns a randomly sized selection when numItems is not provided', () => {
    jsv.assert(
      jsv.forall(jsv.constant(testArray), (arr) => {
        const result = chooseRandom()(arr)
        return result.length > 0 && result.length <= arr.length && result.every((item) => arr.includes(item))
      })
    )
  })

  it('does not modify the original array', () => {
    jsv.assert(
      jsv.forall(jsv.constant(testArray), (arr) => {
        const originalCopy = [...arr]
        chooseRandom()(arr, 3)
        return JSON.stringify(arr) === JSON.stringify(originalCopy)
      })
    )
  })

  it('returns a randomly sized array if numItems is greater than or less than array length', () => {
    jsv.assert(
      jsv.forall(jsv.constant(testArray), jsv.integer(-10, 100), (arr, numItems) => {
        const result = chooseRandom()(arr, numItems)
        return result.length > 0 && result.length <= arr.length && result.every((item) => arr.includes(item))
      })
    )
  })

  it('returns a shuffled subset', () => {
    jsv.assert(
      jsv.forall(jsv.constant(testArray), jsv.integer(1, testArray.length), (arr, numItems) => {
        const result = chooseRandom()(arr, numItems)
        return result.length === numItems && result.every((item) => arr.includes(item))
      })
    )
  })
})

describe('createPrompt', () => {
  const options = { tests: 10 }

  it('Should return an array even if passed in undefined or no object', () => {
    jsv.assert(() => Array.isArray(createPrompt()), options)
    jsv.assert(() => Array.isArray(createPrompt({})), options)
    jsv.assert(() => Array.isArray(createPrompt(undefined)), options)
  })

  it('Should default to 1 question and 2 choices', () => {
    const prompts = [
      { type: 'input', name: 'question-1', message: 'Enter question 1' },
      { type: 'input', name: 'question-1-choice-1', message: 'Enter answer choice 1 for question 1' },
      { type: 'input', name: 'question-1-choice-2', message: 'Enter answer choice 2 for question 1' }
    ]
    jsv.assert(() => _.isEqual(createPrompt(), prompts), options)
  })

  it('Should return an array of length numQuestions + (numQuestions * numChoices)', () =>
    jsv.assertForall(
      jsv.record({ numQuestions: jsv.nat, numChoices: jsv.nat }),
      r => createPrompt(r).length === r.numQuestions + (r.numQuestions * r.numChoices)
    ))

  it('Should return only questions if numChoices is 0', () =>
    jsv.assertForall(
      jsv.nat,
      numQuestions => {
        const prompts = createPrompt({ numQuestions, numChoices: 0 })
        return prompts.length === numQuestions && prompts.every(p => p.name.startsWith('question-') && !p.name.includes('choice'))
      }
    ))

  it('Should return an empty array if numQuestions is 0', () => {
    jsv.assert(() => createPrompt({ numQuestions: 0, numChoices: 2 }).length === 0, options)
  })

  it('Should generate correctly formatted names for all prompts', () =>
    jsv.assertForall(
      jsv.record({ numQuestions: jsv.nat, numChoices: jsv.nat }),
      r => createPrompt(r).every((item, index) =>
        index % (r.numChoices + 1) === 0
          ? item.name.startsWith('question-') && !item.name.includes('choice')
          : item.name.includes('-choice-')
      )
    ))
})

describe('createQuestions', () => {
  const options = { tests: 10 }

  it('Should return an array even if passed in undefined or no object', () => {
    jsv.assert(() => Array.isArray(createQuestions()), options)
    jsv.assert(() => Array.isArray(createQuestions({})), options)
    jsv.assert(() => Array.isArray(createQuestions(undefined)), options)
  })

  it('Should return an empty array if no object is provided', () => {
    jsv.assert(() => createQuestions().length === 0, options)
    jsv.assert(() => createQuestions({}).length === 0, options)
  })

  it('Should return correctly formatted question objects', () =>
    jsv.assertForall(
      jsv.record({
        'question-1': jsv.string,
        'question-1-choice-1': jsv.string,
        'question-1-choice-2': jsv.string
      }),
      r => {
        const result = createQuestions(r)
        return result.length === 1 &&
          result[0].hasOwnProperty('type') &&
          result[0].hasOwnProperty('name') &&
          result[0].hasOwnProperty('message') &&
          result[0].hasOwnProperty('choices')
      }
    ))

  it('Should return an empty choices array for questions with no choices', () => {
    const input = { 'question-1': 'What is your name?' }
    const expectedOutput = [{ type: 'list', name: 'question-1', message: 'What is your name?', choices: [] }]
    jsv.assert(() => _.isEqual(createQuestions(input), expectedOutput), options)
  })

  it('Should return a correctly structured object when multiple questions and choices exist', () =>
    jsv.assertForall(
      jsv.record({
        'question-1': jsv.string,
        'question-1-choice-1': jsv.string,
        'question-1-choice-2': jsv.string,
        'question-2': jsv.string,
        'question-2-choice-1': jsv.string,
        'question-2-choice-2': jsv.string
      }),
      r =>
        _.isEqual(createQuestions(r), [
          {
            type: 'list',
            name: 'question-1',
            message: r['question-1'],
            choices: [r['question-1-choice-1'], r['question-1-choice-2']]
          },
          {
            type: 'list',
            name: 'question-2',
            message: r['question-2'],
            choices: [r['question-2-choice-1'], r['question-2-choice-2']]
          }
        ])
    ))

  it('Should ignore extra properties in the input object', () =>
    jsv.assertForall(
      jsv.record({
        'question-1': jsv.string,
        'question-1-choice-1': jsv.string,
        'question-1-choice-2': jsv.string,
        'extra-key': jsv.string,
        'question': jsv.string
      }),
      r => {
        const result = createQuestions(r)
        return result.length === 1 && result[0].name !== 'extra-key' && result[0].name !== 'question'
      }
    ))
})