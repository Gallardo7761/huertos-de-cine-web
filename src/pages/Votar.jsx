import LoadingIcon from "@/components/LoadingIcon";
import MovieCard from "@/components/Movies/MovieCard";

import { DataProvider } from "@/context/DataContext";

import { useDataContext } from "@/hooks/useDataContext";
import { useConfig } from "@/hooks/useConfig";
import { Alert } from "react-bootstrap";

import { useEffect, useState } from "react";
import { useError } from "@/context/ErrorContext";

const Votar = () => {
  const { config, configLoading } = useConfig();
  const { showError } = useError();

  if (configLoading) return (
    <div className="text-center py-5">
      <div className="spinner-border primary" role="status"></div>
      <p className="mt-2">Cargando...</p>
    </div>
  );

  const reqConfig = {
    baseUrl: `${config.apiConfig.baseUrl}${config.apiConfig.endpoints.movies.all}`,
    params: {},
  };

  return (
    <DataProvider config={reqConfig} onError={showError}>
      <VotarContent />
    </DataProvider>
  );
}

const VotarContent = () => {
  const { data, loading, error, postData, putData, deleteData } = useDataContext();
  const { config } = useConfig();
  const [showAlert, setShowAlert] = useState(() => localStorage.getItem('alertShown') !== 'true');

  const handleCloseAlert = () => {
    localStorage.setItem('alertShown', 'true');
    setShowAlert(false);
  };

  const handleVote = async (movie_id, type) => {
    if (!config) return;

    const identity = JSON.parse(localStorage.getItem('identity') || '{}');
    const userId = identity.user?.userId || identity.userId;

    const action = type === 1 ? 'upvote' : 'downvote';
    const url = `${config.apiConfig.baseUrl}/movies/${movie_id}/${action}`;

    try {
      await postData(url, {}, true, { "X-User-Id": userId });
    } catch (err) {
      console.error(`Error al votar:`, err);
    }
  };

  const handleEdit = async (movie_id, formData) => {
    if (!config) return;
    const url = `${config.apiConfig.baseUrl}/movies/${movie_id}`;
    try {
      await putData(url, formData, true);
    } catch (err) {
      console.error("Error al editar película:", err);
    }
  };

  const handleDelete = async (movie_id) => {
    if (!config) return;
    const url = `${config.apiConfig.baseUrl}/movies/${movie_id}`;
    try {
      await deleteData(url, true);
    } catch (err) {
      console.error("Error al borrar película:", err);
    }
  };

  if (loading) return <div className="text-center py-5"><LoadingIcon /></div>;
  if (error) return <Alert variant="danger">Error: {error.message}</Alert>;

  return (
    <main className="row m-0 p-0 justify-content-center">
      {showAlert && (
        <Alert className="col-10 col-md-6 m-0 mt-3 text-center" variant="warning" dismissible onClose={handleCloseAlert}>
          <strong>Tip: haz click en la portada para ver la descripción</strong>
        </Alert>
      )}

      <div className="row gap-4 mt-4 justify-content-center w-100">
        {data?.map((movie) => (
          <MovieCard
            key={movie.movieId}
            movie_id={movie.movieId}
            title={movie.title}
            description={movie.description}
            cover={movie.cover}
            upvotes={movie.upvotes}
            downvotes={movie.downvotes}
            userVote={movie.userVote}
            onVote={handleVote}
            onEdit={handleEdit}
            onDelete={handleDelete}
          />
        ))}
      </div>
    </main>
  );
}


export default Votar;