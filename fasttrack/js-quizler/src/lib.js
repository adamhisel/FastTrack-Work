import fs from 'fs'

const randomIndex = (max) => Math.floor(Math.random() * max)

const isValidCount = (n, len) => Number.isInteger(n) && n >= 1 && n <= len;

const chooseRandom = (randFn = randomIndex) => (array = [], numItems) => {
  const len = array.length
  if(len <= 1) return array
  const size = isValidCount(numItems, len) ? numItems : (1 + randFn(len));
  return Array.from({ length: size }, () => array[randFn(len)]);
}

const createPrompt = ({ numQuestions = 1, numChoices = 2} = {}) => {
  return (Array.from({length: numQuestions}, (_, i) => ([
    { type: 'input',
      name: `question-${i+1}`,
      message: `Enter question ${i+1}`
    },
    ...Array.from({length: numChoices}, (_, j) => ({
      type: 'input',
      name: `question-${i+1}-choice-${j+1}`,
      message: `Enter answer choice ${j+1} for question ${i+1}`
    }))
  ])).flat())
}

const createQuestions = (answers = {}) => {
   const qNums = Object.keys(answers)
    .filter(k => k.startsWith('question-') && !k.includes('choice'))
    .map(k => Number(k.split('-')[1]))
    .sort((a, b) => a - b);

  if (qNums.length === 0) return [];

  const first = qNums[0];
  const numChoices = Object.keys(answers)
    .filter(k => k.startsWith(`question-${first}-choice-`)).length;

  return qNums.map(q => ({
    type: 'list',
    name: `question-${q}`,
    message: answers[`question-${q}`],
    choices: Array.from({length: numChoices}, (_, i) => 
      answers[`question-${q}-choice-${i+1}`] ?? ''
    )
  }))
  
}

const readFile = path =>
  new Promise((resolve, reject) => {
    fs.readFile(path, (err, data) => (err ? reject(err) : resolve(data)))
  })

const writeFile = (path, data) =>
  new Promise((resolve, reject) => {
    fs.writeFile(path, data, err =>
      err ? reject(err) : resolve('File saved successfully')
    )
  })

export { chooseRandom, createPrompt, createQuestions, readFile, writeFile }