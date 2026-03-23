import SearchToolbar from "@/components/SearchToolbar";
import { useState, useEffect } from "react";
import UserCard from "@/components/Users/UserCard";
import LoadingIcon from "@/components/LoadingIcon";
import { DataProvider } from "@/context/DataContext";
import { useConfig } from "@/hooks/useConfig";
import { useDataContext } from "@/hooks/useDataContext";
import '@/css/Admin.css';
import { useError } from "@/context/ErrorContext";

const Admin = () => {
    const { config, configLoading } = useConfig();
    const { showError } = useError();

    if (configLoading) return (
        <div className="text-center py-5">
            <div className="spinner-border primary" role="status"></div>
            <p className="mt-2">Cargando...</p>
        </div>
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

    if (dataLoading) return (
        <div className="text-center py-5">
            <div className="spinner-border primary" role="status"></div>
            <p className="mt-2">Cargando...</p>
        </div>
    );
    if (dataError) return <p>Error: {dataError.message}</p>;

    const addedUserIds = data.map(identity => identity.user.userId);

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
        <main className="container my-5">
            <h2>Solicitudes</h2>
            <hr />
            <h2>Usuarios añadidos</h2>
            <div className="rounded-4 p-3 user-container">
                <div className="row g-3 m-0">
                    {data
                        .filter(identity => identity.account.status === 1)
                        .map((identity) => (
                            <UserCard
                                key={identity.user.userId}
                                identity={identity}
                                renderMode="delete"
                                onDelete={() => handleDelete(u)}
                            />
                        ))
                    }
                </div>
            </div>
        </main>
    );
}

export default Admin;