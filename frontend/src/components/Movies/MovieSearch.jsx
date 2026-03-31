import { useEffect, useRef, useState } from 'react';
import { searchMovies, getImageUrl } from '@/services/tmdbService';
import '@/css/MovieSearch.css';

const MovieSearch = ({ onSelect }) => {
    const [query, setQuery] = useState('');
    const [results, setResults] = useState([]);
    const searchRef = useRef(null);

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (searchRef.current && !searchRef.current.contains(event.target)) {
                setResults([]);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    const handleSearch = async (e) => {
        const val = e.target.value;
        setQuery(val);
        if (val.length > 2) {
            const movies = await searchMovies(val);
            setResults(movies);
        } else {
            setResults([]);
        }
    };

    const selectMovie = (m) => {
        onSelect({
            title: m.title,
            description: m.overview,
            cover: getImageUrl(m.poster_path)
        });
        setResults([]);
        setQuery('');
    };

    return (
        <div className="tmdb-search-container" ref={searchRef}>
            <input
                type="text"
                className="form-control themed-input"
                placeholder="Buscar en TMDB..."
                value={query}
                onChange={handleSearch}
                onFocus={handleSearch}
            />

            {results.length > 0 && (
                <div className="tmdb-results-list shadow-lg">
                    {results.map(m => (
                        <div
                            key={m.id}
                            className="tmdb-result-item"
                            onClick={() => selectMovie(m)}
                        >
                            <img
                                src={getImageUrl(m.poster_path) || 'https://via.placeholder.com/45x68?text=No+Img'}
                                alt={m.title}
                            />
                            <div className="tmdb-result-info">
                                <span className="tmdb-result-title">{m.title}</span>
                                <span className="tmdb-result-year">
                                    {m.release_date ? m.release_date.split('-')[0] : 'S/N'}
                                </span>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default MovieSearch;