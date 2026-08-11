import { Suspense, lazy } from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { MainLayout } from '../layouts/MainLayout';
import { AuthLayout } from '../layouts/AuthLayout';
import { AdminLayout } from '../layouts/AdminLayout';
import { AdminProtectedRoute } from './AdminProtectedRoute';
import { Loader2 } from 'lucide-react';

// Lazy loaded pages
const Dashboard = lazy(() => import('../pages/Dashboard').then(m => ({ default: m.Dashboard })));
const Login = lazy(() => import('../pages/Login').then(m => ({ default: m.Login })));
const Register = lazy(() => import('../pages/Register').then(m => ({ default: m.Register })));
const Units = lazy(() => import('../pages/Units').then(m => ({ default: m.Units })));
const UnitDetail = lazy(() => import('../pages/UnitDetail').then(m => ({ default: m.UnitDetail })));
const Learn = lazy(() => import('../pages/Learn').then(m => ({ default: m.Learn })));
const Quiz = lazy(() => import('../pages/Quiz').then(m => ({ default: m.Quiz })));
const QuizResult = lazy(() => import('../pages/QuizResult').then(m => ({ default: m.QuizResult })));
const AdminDashboard = lazy(() => import('../pages/admin/AdminDashboard').then(m => ({ default: m.AdminDashboard })));
const AdminUnits = lazy(() => import('../pages/admin/AdminUnits').then(m => ({ default: m.AdminUnits })));
const AdminVocabularies = lazy(() => import('../pages/admin/AdminVocabularies').then(m => ({ default: m.AdminVocabularies })));
const AdminQuestions = lazy(() => import('../pages/admin/AdminQuestions').then(m => ({ default: m.AdminQuestions })));

// Loading Screen
const LoadingScreen = () => (
  <div className="flex flex-col items-center justify-center min-h-[50vh]">
    <Loader2 className="w-8 h-8 text-primary animate-spin mb-4" />
    <p className="text-gray-500 font-medium">Đang tải dữ liệu...</p>
  </div>
);

// Placeholder components for unimplemented pages
const Placeholder = ({ title }: { title: string }) => (
  <div className="flex items-center justify-center h-64 bg-white rounded-xl shadow-sm border border-gray-100">
    <h2 className="text-xl font-medium text-gray-500">{title} (Coming Soon)</h2>
  </div>
);

export const AppRouter = () => {
  return (
    <Suspense fallback={<LoadingScreen />}>
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
    </Suspense>
  );
};
