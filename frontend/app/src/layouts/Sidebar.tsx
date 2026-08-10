
import { NavLink } from 'react-router-dom';
import { Book, CheckSquare, BarChart, User } from 'lucide-react';

export const Sidebar = () => {
  const links = [
    { name: 'Dashboard', path: '/dashboard', icon: BarChart },
    { name: 'Units', path: '/units', icon: Book },
    { name: 'Grammar', path: '/grammar', icon: CheckSquare },
    { name: 'Profile', path: '/profile', icon: User },
  ];

  return (
    <div className="w-64 bg-white border-r border-gray-200 h-[calc(100vh-4rem)] sticky top-16 overflow-y-auto hidden md:block">
      <div className="p-4">
        <div className="text-xs font-semibold text-gray-400 uppercase tracking-wider mb-4">
          Learning Journey
        </div>
        <nav className="space-y-1">
          {links.map((link) => {
            const Icon = link.icon;
            return (
              <NavLink
                key={link.name}
                to={link.path}
                className={({ isActive }) =>
                  `group flex items-center px-3 py-2 text-sm font-medium rounded-md transition-colors ${
                    isActive
                      ? 'bg-red-50 text-primary'
                      : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900'
                  }`
                }
              >
                {({ isActive }) => (
                  <>
                    <Icon
                      className={`mr-3 h-5 w-5 flex-shrink-0 ${
                        isActive ? 'text-primary' : 'text-gray-400 group-hover:text-gray-500'
                      }`}
                    />
                    {link.name}
                  </>
                )}
              </NavLink>
            );
          })}
        </nav>
      </div>
      
    </div>
  );
};
