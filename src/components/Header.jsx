import '@/css/Header.css';
import { Link } from 'react-router-dom';
import Navbar from "@/components/NavBar";
import IfAuthenticated from "@/components/Auth/IfAuthenticated";
import { useAuth } from "@/hooks/useAuth";
import { useNavigate } from 'react-router-dom';
import IfRole from '@/components/Auth/IfRole';
import { CONSTANTS } from '@/util/constants';
import IfNotAuthenticated from './Auth/IfNotAuthenticated';

const Header = () => {
    const { logout } = useAuth();
    const navigate = useNavigate();
    const identity = JSON.parse(localStorage.getItem("identity")) ?? "invitado";

    const handleLogout = () => {
        logout();
        navigate('/login', { replace: true });
    }

    return (
        <>
            <header className={`text-center header p-4 d-flex flex-column justify-content-center align-items-center`}>
                <Link to='/' className='text-decoration-none'>
                    <h1>Huer🌱os de 🎬ine</h1>
                </Link>
            </header>
            <Navbar
                rightContent={
                    <div className='d-flex m-0 p-0 gap-4 flex-column flex-lg-row align-items-lg-center'>
                        <IfAuthenticated>
                            <li className="nav-item">
                                <Link to="/" className="nav-link p-0">
                                    votos
                                </Link>
                            </li>
                            <IfRole roles={[CONSTANTS.ROLE_ADMIN, CONSTANTS.ROLE_DEV]}>
                                <li className="nav-item">
                                    <Link to="/admin" className="nav-link p-0">
                                        admin
                                    </Link>
                                </li>
                            </IfRole>
                            <Link to="/login" onClick={handleLogout} className="nav-link p-0">
                                Cerrar sesión
                            </Link>
                        </IfAuthenticated>
                        <IfNotAuthenticated>
                            <Link to="/login" onClick={handleLogout} className="nav-link p-0">
                                Iniciar sesión
                            </Link>
                            <Link to="/apuntarse" onClick={handleLogout} className="nav-link p-0">
                                Apuntarse
                            </Link>
                        </IfNotAuthenticated>
                    </div>
                }
            >
                <IfAuthenticated>
                    <li className="nav-item user-name nav-link p-0">{`@${identity?.account?.username}`}</li>
                </IfAuthenticated>
            </Navbar>
        </>
    );
}

export default Header;