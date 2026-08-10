import { Routes, Route, Navigate } from 'react-router-dom';
import { MainLayout } from '../layouts/MainLayout';
import { AuthLayout } from '../layouts/AuthLayout';
import { AdminLayout } from '../layouts/AdminLayout';
import { Dashboard } from '../pages/Dashboard';
import { Login } from '../pages/Login';
import { Register } from '../pages/Register';
import { Units } from '../pages/Units';
import { UnitDetail } from '../pages/UnitDetail';
import { Learn } from '../pages/Learn';
import { Quiz } from '../pages/Quiz';
import { QuizResult } from '../pages/QuizResult';
import { AdminProtectedRoute } from './AdminProtectedRoute';
import { AdminDashboard } from '../pages/admin/AdminDashboard';
import { AdminUnits } from '../pages/admin/AdminUnits';
import { AdminVocabularies } from '../pages/admin/AdminVocabularies';
import { AdminQuestions } from '../pages/admin/AdminQuestions';

// Placeholder components for unimplemented pages
const Placeholder = ({ title }: { title: string }) => (
  <div className="flex items-center justify-center h-64 bg-white rounded-xl shadow-sm border border-gray-100">
    <h2 className="text-xl font-medium text-gray-500">{title} (Coming Soon)</h2>
  </div>
);

export const AppRouter = () => {
  return (
    <Routes>
      <Route element={<AuthLayout />}>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Route>

      <Route element={<MainLayout />}>
        <Route path="/" element={<Navigate to="/units" replace />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/units" element={<Units />} />
        <Route path="/units/:id" element={<UnitDetail />} />
        <Route path="/units/:id/learn" element={<Learn />} />
        <Route path="/units/:id/quiz" element={<Quiz />} />
        <Route path="/quiz/:attemptId/result" element={<QuizResult />} />
        <Route path="/grammar" element={<Placeholder title="Grammar" />} />
        <Route path="/profile" element={<Placeholder title="Profile" />} />
      </Route>

      <Route element={<AdminProtectedRoute />}>
        <Route path="/admin" element={<AdminLayout />}>
          <Route index element={<AdminDashboard />} />
          <Route path="units" element={<AdminUnits />} />
          <Route path="vocabularies" element={<AdminVocabularies />} />
          <Route path="questions" element={<AdminQuestions />} />
        </Route>
      </Route>

      <Route path="*" element={<Navigate to="/units" replace />} />
    </Routes>
  );
};
