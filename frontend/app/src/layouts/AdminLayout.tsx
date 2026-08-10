import { useState } from 'react';
import { Outlet, useNavigate, NavLink } from 'react-router-dom';
import { LogOut, LayoutDashboard, Database, ListChecks, ChevronLeft, ChevronRight } from 'lucide-react';
import { useAuth } from '../store/AuthContext';

export const AdminLayout = () => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();
  const [isCollapsed, setIsCollapsed] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const adminLinks = [
    { name: 'Dashboard', path: '/admin', icon: LayoutDashboard },
    { name: 'Units', path: '/admin/units', icon: Database },
    { name: 'Vocabularies', path: '/admin/vocabularies', icon: Database },
    { name: 'Questions', path: '/admin/questions', icon: ListChecks },
  ];

  return (
    <div className="min-h-screen bg-gray-100 flex flex-col font-sans">
      <header className="bg-gray-900 text-white border-b border-gray-800">
        <div className="w-full mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-14">
            <div className="flex items-center gap-2">
              <span className="bg-primary text-white text-xs font-bold px-2 py-1 rounded">ADMIN</span>
              <img src="/assets/hikari_logo.png" alt="Nihongo Logo" className="w-12 h-12 rounded-full object-cover" />
              <span className="font-semibold text-xl text-red-600 tracking-tight">Nihongo</span>
            </div>
            <div className="flex items-center gap-4">
              <span className="text-sm text-gray-300">{user?.username || 'Admin'}</span>
              <button onClick={handleLogout} className="text-gray-400 hover:text-white transition-colors">
                <LogOut className="h-5 w-5" />
              </button>
            </div>
          </div>
        </div>
      </header>

      <div className="flex flex-1 w-full mx-auto">
        <div className={`${isCollapsed ? 'w-20' : 'w-64'} bg-gray-800 hidden md:flex md:flex-col transition-all duration-300 relative`}>
          <button 
            onClick={() => setIsCollapsed(!isCollapsed)}
            className="absolute -right-3 top-6 bg-gray-700 text-white rounded-full p-1 shadow-md hover:bg-gray-600 transition-colors z-10"
          >
            {isCollapsed ? <ChevronRight className="w-4 h-4" /> : <ChevronLeft className="w-4 h-4" />}
          </button>
          
          <nav className="p-4 space-y-1 flex-1">
            {adminLinks.map((link) => {
              const Icon = link.icon;
              return (
                <NavLink
                  key={link.name}
                  to={link.path}
                  end={link.path === '/admin'}
                  className={({ isActive }) =>
                    `group flex items-center px-3 py-2 text-sm font-medium rounded-md transition-colors ${
                      isActive
                        ? 'bg-gray-900 text-white'
                        : 'text-gray-300 hover:bg-gray-700 hover:text-white'
                    } ${isCollapsed ? 'justify-center' : ''}`
                  }
                  title={isCollapsed ? link.name : undefined}
                >
                  {({ isActive }) => (
                    <>
                      <Icon
                        className={`${isCollapsed ? '' : 'mr-3'} h-5 w-5 flex-shrink-0 ${
                          isActive ? 'text-primary' : 'text-gray-400 group-hover:text-gray-300'
                        }`}
                      />
                      {!isCollapsed && <span>{link.name}</span>}
                    </>
                  )}
                </NavLink>
              );
            })}
          </nav>
        </div>
        
        <main className="flex-1 p-6 overflow-y-auto">
          <Outlet />
        </main>
      </div>
    </div>
  );
};
