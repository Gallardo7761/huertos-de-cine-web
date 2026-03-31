const TMDB_API_KEY = import.meta.env.VITE_TMDB_API_KEY; 
const BASE_URL = 'https://api.themoviedb.org/3';

export const searchMovies = async (query) => {
    if (!query) return [];
    const response = await fetch(
        `${BASE_URL}/search/movie?api_key=${TMDB_API_KEY}&query=${encodeURIComponent(query)}&language=es-ES`
    );
    const data = await response.json();
    return data.results || [];
};

export const getImageUrl = (path) => path ? `https://image.tmdb.org/t/p/w500${path}` : null;