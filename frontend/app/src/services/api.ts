import api from '../api/axios';
import type { Unit, Vocabulary, Question, User } from '../types';

export const authApi = {
  login: async (email: string, password: string) => {
    const response = await api.post('/auth/login', { email, password });
    return response.data; // { message: "OTP sent" }
  },
  verifyOtp: async (email: string, otp: string) => {
    const response = await api.post('/auth/verify-otp', { email, otp });
    return response.data; // { token: "..." }
  },
  register: async (username: string, email: string, password: string) => {
    const response = await api.post('/auth/register', { username, email, password });
    return response.data; // { token: "..." }
  },
  getCurrentUser: async (): Promise<User> => {
    const response = await api.get('/users/me');
    return response.data;
  }
};

const cache: Record<string, any> = {};

export const unitApi = {
  getUnits: async (): Promise<Unit[]> => {
    if (cache['units']) return cache['units'];
    const response = await api.get('/units');
    cache['units'] = response.data;
    return response.data;
  },
  getUnitById: async (id: number): Promise<Unit> => {
    const response = await api.get(`/units/${id}`);
    return response.data;
  }
};

export const vocabApi = {
  getVocabByUnit: async (unitId: number): Promise<Vocabulary[]> => {
    const cacheKey = `vocab_${unitId}`;
    if (cache[cacheKey]) return cache[cacheKey];
    const response = await api.get(`/units/${unitId}/vocabularies`);
    cache[cacheKey] = response.data;
    return response.data;
  },
  getReviewVocab: async (): Promise<Vocabulary[]> => {
    const response = await api.get('/vocabularies/review');
    return response.data;
  }
};

export const quizApi = {
  getQuestions: async (unitId: number): Promise<Question[]> => {
    const response = await api.get(`/units/${unitId}/quiz`);
    return response.data;
  },
  startAttempt: async (unitId: number) => {
    const response = await api.post('/quiz/attempts', { unitId });
    return response.data;
  },
  submitAnswer: async (attemptId: number, questionId: number, selectedOption: string) => {
    const response = await api.post(`/quiz/attempts/${attemptId}/answers`, { questionId, selectedOption });
    return response.data;
  },
  submitQuiz: async (attemptId: number) => {
    const response = await api.post(`/quiz/attempts/${attemptId}/submit`);
    return response.data; // Should return attempt result
  }
};

export const dashboardApi = {
  getProgress: async () => {
    const response = await api.get('/dashboard');
    return response.data;
  }
};

export const adminApi = {
  getDashboardStats: async () => {
    const response = await api.get('/admin/dashboard');
    return response.data;
  },
  createUnit: async (unit: Partial<Unit>) => {
    const response = await api.post('/admin/units', unit);
    return response.data;
  },
  updateUnit: async (id: number, unit: Partial<Unit>) => {
    const response = await api.put(`/admin/units/${id}`, unit);
    return response.data;
  },
  deleteUnit: async (id: number) => {
    await api.delete(`/admin/units/${id}`);
  },
  createVocabulary: async (unitId: number, voc: Partial<Vocabulary>) => {
    const response = await api.post(`/admin/units/${unitId}/vocabularies`, voc);
    return response.data;
  },
  updateVocabulary: async (id: number, voc: Partial<Vocabulary>) => {
    const response = await api.put(`/admin/vocabularies/${id}`, voc);
    return response.data;
  },
  deleteVocabulary: async (id: number) => {
    await api.delete(`/admin/vocabularies/${id}`);
  },
  createQuestion: async (unitId: number, vocabId: number, question: Partial<Question>) => {
    const response = await api.post(`/admin/units/${unitId}/vocabularies/${vocabId}/questions`, question);
    return response.data;
  },
  updateQuestion: async (id: number, question: Partial<Question>) => {
    const response = await api.put(`/admin/questions/${id}`, question);
    return response.data;
  },
  deleteQuestion: async (id: number) => {
    await api.delete(`/admin/questions/${id}`);
  }
};
