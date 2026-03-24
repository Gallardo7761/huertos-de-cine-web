import { useMemo } from "react";
import UserCard from "@/components/Users/UserCard";
import LoadingIcon from "@/components/LoadingIcon";
import { DataProvider } from "@/context/DataContext";
import { useConfig } from "@/hooks/useConfig";
import { useDataContext } from "@/hooks/useDataContext";
import '@/css/Admin.css';
import { useError } from "@/context/ErrorContext";
import { Alert, Badge } from "react-bootstrap";

const Admin = () => {
    const { config, configLoading } = useConfig();
    const { showError } = useError();

    if (configLoading) return (
        <section className="text-center py-5" role="status" aria-live="polite" aria-label="Cargando administración">
            <div className="spinner-border primary" role="status"></div>
            <p className="mt-2">Cargando...</p>
        </section>
    );

    const reqConfig = {
        baseUrl: `${config.apiConfig.baseUrl}${config.apiConfig.endpoints.viewers.all}`,
        params: {},
    };

    return (
        <DataProvider config={reqConfig} onError={showError}>
            <AdminContent reqConfig={reqConfig} />
        </DataProvider>
    );
}

const AdminContent = ({ reqConfig }) => {
    const { data, dataLoading, dataError, getData, postData } = useDataContext();

    const identities = useMemo(() => (Array.isArray(data) ? data : []), [data]);

    const pendingIdentities = identities.filter(identity => identity?.account?.status !== 1);
    const activeIdentities = identities.filter(identity => identity?.account?.status === 1);

    if (dataLoading) return (
        <section className="text-center py-5" role="status" aria-live="polite" aria-label="Cargando usuarios">
            <LoadingIcon />
        </section>
    );
    if (dataError) return (
        <main className="container py-4" role="main">
            <Alert variant="danger" role="alert">Error: {dataError.message}</Alert>
        </main>
    );

    const handleAdd = async (identity) => {
        try {
            await postData(reqConfig.baseUrl, {
                userId: identity.user.userId,
                role: identity.account?.role ?? 1,
                status: 1,
            });
        } catch (error) {
            console.error('Error añadiendo usuario:', error);
        }
    }

    const handleDelete = async (identity) => {
        try {
            await postData(reqConfig.baseUrl, {
                userId: identity.user.userId,
                role: 0,
                status: 0,
            });
        } catch (error) {
            console.error('Error borrando usuario:', error);
        }
    }

    return (
        <main className="container py-4 admin-page py-lg-5" aria-labelledby="admin-title">
            <header className="admin-page-header mb-4">
                <h1 id="admin-title" className="mb-2">Administración de usuarios</h1>
                <p className="admin-subtitle m-0">Gestiona solicitudes y usuarios activos desde un único panel.</p>
            </header>

            <section className="admin-section mb-4" aria-labelledby="pending-users-title">
                <div className="d-flex justify-content-between align-items-center gap-3 mb-2 flex-wrap">
                    <h2 id="pending-users-title" className="m-0">Solicitudes</h2>
                    <small>{pendingIdentities.length === 1 ? `${pendingIdentities.length} solicitud` : `${pendingIdentities.length} solicitudes`}</small>
                </div>

                <div className="rounded-4 p-3 user-container" role="region" aria-label="Lista de solicitudes pendientes">
                    <div className="row g-3 m-0">
                        {pendingIdentities.length === 0 ? (
                            <p className="admin-empty-state m-0" role="status">No hay solicitudes pendientes.</p>
                        ) : (
                            pendingIdentities.map((identity) => (
                                <UserCard
                                    key={identity.user.userId}
                                    identity={identity}
                                    renderMode="add"
                                    onAdd={() => handleAdd(identity)}
                                />
                            ))
                        )}
                    </div>
                </div>
            </section>

            <section className="admin-section mb-4" aria-labelledby="active-users-title">
                <div className="d-flex justify-content-between align-items-center gap-3 mb-2 flex-wrap">
                    <h2 id="active-users-title" className="m-0">Usuarios añadidos</h2>
                    <small>{activeIdentities.length === 1 ? `${activeIdentities.length} usuario` : `${activeIdentities.length} usuarios`}</small>
                </div>

                <div className="rounded-4 p-3 user-container" role="region" aria-label="Lista de usuarios activos">
                    <div className="row g-3 m-0">
                        {activeIdentities.length === 0 ? (
                            <p className="admin-empty-state m-0" role="status">No hay usuarios activos.</p>
                        ) : (
                            activeIdentities.map((identity) => (
                                <UserCard
                                    key={identity.user.userId}
                                    identity={identity}
                                    renderMode="delete"
                                    onDelete={() => handleDelete(identity)}
                                />
                            ))
                        )}
                    </div>
                </div>
            </section>

            <section className="admin-section" aria-labelledby="peliculas-title">
                <div className="d-flex justify-content-between align-items-center gap-3 mb-2 flex-wrap">
                    <h2 id="peliculas-title" className="m-0">Películas</h2>
                </div>
            </section>
        </main>
    );
}

export default Admin;