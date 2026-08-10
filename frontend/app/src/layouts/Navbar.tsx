
import { Link, NavLink, useNavigate } from 'react-router-dom';
import { LogOut, Settings } from 'lucide-react';
import { useAuth } from '../store/AuthContext';

export const Navbar = () => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header className="bg-white border-b border-gray-200 sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex">
            <div className="flex-shrink-0 flex items-center gap-2 cursor-pointer" onClick={() => navigate('/units')}>
              <img src="/assets/hikari_logo.png" alt="Nihongo Logo" className="w-16 h-16 rounded-full object-cover" />
              <span className="font-bold text-2xl text-red-600 tracking-tight">
                Nihongo
              </span>
            </div>
            <nav className="hidden sm:ml-6 sm:flex sm:space-x-8">
              <NavLink to="/units" className={({ isActive }) => `inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium ${isActive ? 'border-primary text-gray-900' : 'border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700'}`}>
                Units
              </NavLink>
              <NavLink to="/grammar" className={({ isActive }) => `inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium ${isActive ? 'border-primary text-gray-900' : 'border-transparent text-gray-500 hover:border-gray-300 hover:text-gray-700'}`}>
                Grammar
              </NavLink>
            </nav>
          </div>
          <div className="hidden sm:ml-6 sm:flex sm:items-center gap-4">
            {user?.role === 'ADMIN' && (
              <Link to="/admin" className="text-xs font-bold bg-primary text-white px-3 py-1.5 rounded-full hover:bg-primary-dark transition">
                ADMIN PANEL
              </Link>
            )}
            <button className="p-1 rounded-full text-gray-400 hover:text-gray-500 focus:outline-none">
              <Settings className="h-5 w-5" />
            </button>
            <div className="relative flex items-center gap-3 border-l border-gray-200 pl-4">
              <span className="text-sm font-medium text-gray-700">{user?.username || 'User'}</span>
              <div className="h-8 w-8 rounded-full bg-gray-200 border border-gray-300 flex items-center justify-center text-gray-600 font-bold">
                {user?.username?.[0]?.toUpperCase() || 'U'}
              </div>
              <button onClick={handleLogout} className="p-1 text-gray-400 hover:text-primary transition-colors">
                <LogOut className="h-5 w-5" />
              </button>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
};
