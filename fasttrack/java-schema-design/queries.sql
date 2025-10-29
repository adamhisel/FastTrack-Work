INSERT INTO location_table (city, state, country) values ('Nashville', 'Tennessee', 'United States');
INSERT INTO location_table (city, state, country) values ('Memphis', 'Tennessee', 'United States');
INSERT INTO location_table (city, state, country) values ('Phoenix', 'Arizona', 'United States');
INSERT INTO location_table (city, state, country) values ('Denver', 'Colorado', 'United States');

INSERT INTO person_table (first_name, last_name, age, location_id) values ('Chickie', 'Ourtic', 21, 1);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Hilton', 'O''Hanley', 37, 1);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Barbe', 'Purver', 50, 3);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Reeta', 'Sammons', 34, 2);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Abbott', 'Fisbburne', 49, 1);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Winnie', 'Whines', 19, 4);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Samantha', 'Leese', 35, 2);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Edouard', 'Lorimer', 29, 1);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Mattheus', 'Shaplin', 27, 3);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Donnell', 'Corney', 25, 3);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Wallis', 'Kauschke', 28, 3);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Melva', 'Lanham', 20, 2);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Amelina', 'McNirlan', 22, 4);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Courtney', 'Holley', 22, 1);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Sigismond', 'Vala', 21, 4);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Jacquelynn', 'Halfacre', 24, 2);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Alanna', 'Spino', 25, 3);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Isa', 'Slight', 32, 1);
INSERT INTO person_table (first_name, last_name, age, location_id) values ('Kakalina', 'Renne', 26, 3);

INSERT INTO interest_table (title) values ('Programming');
INSERT INTO interest_table (title) values ('Gaming');
INSERT INTO interest_table (title) values ('Computers');
INSERT INTO interest_table (title) values ('Music');
INSERT INTO interest_table (title) values ('Movies');
INSERT INTO interest_table (title) values ('Cooking');
INSERT INTO interest_table (title) values ('Sports');

INSERT INTO person_interest (person_id, interest_id) values (1, 1);
INSERT INTO person_interest (person_id, interest_id) values (1, 2);
INSERT INTO person_interest (person_id, interest_id) values (1, 6);
INSERT INTO person_interest (person_id, interest_id) values (2, 1);
INSERT INTO person_interest (person_id, interest_id) values (2, 7);
INSERT INTO person_interest (person_id, interest_id) values (2, 4);
INSERT INTO person_interest (person_id, interest_id) values (3, 1);
INSERT INTO person_interest (person_id, interest_id) values (3, 3);
INSERT INTO person_interest (person_id, interest_id) values (3, 4);
INSERT INTO person_interest (person_id, interest_id) values (4, 1);
INSERT INTO person_interest (person_id, interest_id) values (4, 2);
INSERT INTO person_interest (person_id, interest_id) values (4, 7);
INSERT INTO person_interest (person_id, interest_id) values (5, 6);
INSERT INTO person_interest (person_id, interest_id) values (5, 3);
INSERT INTO person_interest (person_id, interest_id) values (5, 4);
INSERT INTO person_interest (person_id, interest_id) values (6, 2);
INSERT INTO person_interest (person_id, interest_id) values (6, 7);
INSERT INTO person_interest (person_id, interest_id) values (7, 1);
INSERT INTO person_interest (person_id, interest_id) values (7, 3);
INSERT INTO person_interest (person_id, interest_id) values (8, 2);
INSERT INTO person_interest (person_id, interest_id) values (8, 4);
INSERT INTO person_interest (person_id, interest_id) values (9, 5);
INSERT INTO person_interest (person_id, interest_id) values (9, 6);
INSERT INTO person_interest (person_id, interest_id) values (10, 7);
INSERT INTO person_interest (person_id, interest_id) values (10, 5);
INSERT INTO person_interest (person_id, interest_id) values (11, 1);
INSERT INTO person_interest (person_id, interest_id) values (11, 2);
INSERT INTO person_interest (person_id, interest_id) values (11, 5);
INSERT INTO person_interest (person_id, interest_id) values (12, 1);
INSERT INTO person_interest (person_id, interest_id) values (12, 4);
INSERT INTO person_interest (person_id, interest_id) values (12, 5);
INSERT INTO person_interest (person_id, interest_id) values (13, 2);
INSERT INTO person_interest (person_id, interest_id) values (13, 3);
INSERT INTO person_interest (person_id, interest_id) values (13, 7);
INSERT INTO person_interest (person_id, interest_id) values (14, 2);
INSERT INTO person_interest (person_id, interest_id) values (14, 4);
INSERT INTO person_interest (person_id, interest_id) values (14, 6);
INSERT INTO person_interest (person_id, interest_id) values (15, 1);
INSERT INTO person_interest (person_id, interest_id) values (15, 5);
INSERT INTO person_interest (person_id, interest_id) values (15, 7);
INSERT INTO person_interest (person_id, interest_id) values (16, 2);
INSERT INTO person_interest (person_id, interest_id) values (16, 3);
INSERT INTO person_interest (person_id, interest_id) values (16, 4);
INSERT INTO person_interest (person_id, interest_id) values (17, 1);
INSERT INTO person_interest (person_id, interest_id) values (17, 3);
INSERT INTO person_interest (person_id, interest_id) values (17, 5);
INSERT INTO person_interest (person_id, interest_id) values (17, 7);
INSERT INTO person_interest (person_id, interest_id) values (18, 2);
INSERT INTO person_interest (person_id, interest_id) values (18, 4);
INSERT INTO person_interest (person_id, interest_id) values (18, 6);
INSERT INTO person_interest (person_id, interest_id) values (19, 1);
INSERT INTO person_interest (person_id, interest_id) values (19, 2);
INSERT INTO person_interest (person_id, interest_id) values (19, 3);
INSERT INTO person_interest (person_id, interest_id) values (19, 4);
INSERT INTO person_interest (person_id, interest_id) values (19, 5);
INSERT INTO person_interest (person_id, interest_id) values (19, 6);
INSERT INTO person_interest (person_id, interest_id) values (19, 7);

UPDATE person_table SET age = age + 1 WHERE id = 1;
UPDATE person_table SET age = age + 1 WHERE id = 6;
UPDATE person_table SET age = age + 1 WHERE id = 8;
UPDATE person_table SET age = age + 1 WHERE id = 14;
UPDATE person_table SET age = age + 1 WHERE id = 12;
UPDATE person_table SET age = age + 1 WHERE id = 18;
UPDATE person_table SET age = age + 1 WHERE id = 5;
UPDATE person_table SET age = age + 1 WHERE id = 4;

DELETE FROM person_interest WHERE person_id = 2;
DELETE FROM person_interest WHERE person_id = 17;
DELETE FROM person_table WHERE id = 2;
DELETE FROM person_table WHERE id = 17;

SELECT PT.first_name, PT.last_name FROM person_table AS PT;

SELECT PT.first_name, PT.last_name, L.city, L.state FROM 
	person_table AS PT INNER JOIN location_table AS L 
	ON PT.location_id = L.id 
	WHERE L.city = 'Nashville' AND L.state = 'Tennessee';

SELECT L.city, COUNT(L.city) FROM 
	person_table AS PT INNER JOIN location_table AS L 
	ON PT.location_id = L.id GROUP BY(L.city);

SELECT I.title, COUNT(I.title) FROM 
	person_interest AS PI INNER JOIN interest_table AS I
	ON PI.interest_id = I.id GROUP BY(I.title);

SELECT PT.first_name, PT.last_name, L.city, L.state, I.title 
	FROM person_table AS PT 
	INNER JOIN person_interest AS PI ON PI.person_id = PT.id
	INNER JOIN interest_table AS I ON PI.interest_id = I.id
	INNER JOIN location_table AS L ON PT.location_id = L.id 
	WHERE L.city = 'Nashville' AND L.state = 'Tennessee' AND I.title = 'Programming';

	
SELECT range, COUNT(*) AS count
FROM (
  SELECT CASE
           WHEN age >= 20 AND age < 29 THEN '20-29'
           WHEN age >= 30 AND age < 39 THEN '30-39'
           WHEN age >= 40 AND age <= 50 THEN '40-50'
         END AS range
  FROM person_table
  WHERE age >= 20 AND age <= 50
) x
GROUP BY range
ORDER BY range;
	
