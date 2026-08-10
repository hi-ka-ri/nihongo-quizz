export interface User {
  id: number;
  username: string;
  email: string;
  role: 'USER' | 'ADMIN';
  avatar?: string;
}

export interface Unit {
  id: number;
  title: string;
  description: string;
  level: string; // e.g. N5, N4
  vocabCount: number;
  progress: number; // 0 to 100
  imageUrl?: string;
}

export interface Vocabulary {
  id: number;
  unitId: number;
  kanji: string;
  hiragana: string;
  romaji: string;
  meaning: string;
  exampleSentence: string;
  exampleMeaning: string;
  sinoVietnamese?: string;
}

export interface Question {
  id: number;
  unitId: number;
  vocabularyId?: number;
  questionType: string;
  questionText: string;
  optionA: string;
  optionB: string;
  optionC: string;
  optionD: string;
  correctAnswer: string;
  vocabulary: Vocabulary;
}

export interface QuizAttempt {
  id: number;
  unitId: number;
  userId: number;
  score: number;
  totalQuestions: number;
  date: string;
}
