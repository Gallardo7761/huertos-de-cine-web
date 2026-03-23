import LoadingIcon from "@/components/LoadingIcon";
import MovieCard from "@/components/Movies/MovieCard";

import { DataProvider } from "@/context/DataContext";

import { useDataContext } from "@/hooks/useDataContext";
import { useConfig } from "@/hooks/useConfig";
import { Alert } from "react-bootstrap";

import { useEffect, useState } from "react";

const Votar = () => {
    const { config, configLoading } = useConfig();

    if (configLoading) return <p><LoadingIcon /></p>;

    const reqConfig = {
        baseUrl: `${config.apiConfig.baseUrl}${config.apiConfig.endpoints.movies.all}`,
        params: {},
    };

    return (
        <DataProvider config={reqConfig}>
            <VotarContent />
        </DataProvider>
    );
}

const VotarContent = () => {
  const { data, loading, error } = useDataContext();
  const [showAlert, setShowAlert] = useState(() => localStorage.getItem('alertShown') !== 'true');

  const handleCloseAlert = () => {
    localStorage.setItem('alertShown', 'true');
    setShowAlert(false);
  };

  if (loading) return <LoadingIcon />;
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
          />
        ))}
      </div>
    </main>
  );
}


export default Votar;