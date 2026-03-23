import '@/css/MovieCard.css';
import { useState } from 'react';
import CustomModal from '@/components/CustomModal';
import { faAlignCenter, faCancel, faEdit, faImage, faPenFancy, faSave, faThumbsDown, faThumbsUp, faTrash } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useData } from '@/hooks/useData';
import { useConfig } from '@/hooks/useConfig';
import { Button, Form, Alert } from 'react-bootstrap';
import IfRole from '@/components/Auth/IfRole';
import { CONSTANTS } from '@/util/constants';
import EditMovieForm from './EditMovieForm';
import { useAuth } from '@/hooks/useAuth';

const MovieCard = ({ movie_id, title, description, cover, upvotes, downvotes, userVote }) => {
  const [modal, setModal] = useState(false);
  const [editModal, setEditModal] = useState(false);
  const [deleteTarget, setDeleteTarget] = useState(null);

  const { putData, postData, deleteData } = useData();
  const { config } = useConfig();
  const { authStatus } = useAuth();

  const totalScore = upvotes - downvotes;

  const handleVoteAction = async (type) => {
    if (!config) return;
    const identity = JSON.parse(localStorage.getItem('identity') || '{}');
    const userId = identity.user?.userId || identity.userId;

    const action = type === 1 ? 'upvote' : 'downvote';
    const url = `${config.apiConfig.baseUrl}/movies/${movie_id}/${action}`;

    try {
      await postData(url, {}, true, { "X-User-Id": userId });
    } catch (err) {
      console.error(`Error al hacer ${action}:`, err);
    }
  };

  const handleEdit = async (formData) => {
    const editUrl = `${config.apiConfig.baseUrl}/movies/${movie_id}`;

    const data = {
      title: formData.title,
      description: formData.description,
      cover: formData.cover,
    };

    try {
      await putData(editUrl, data, true);
      setEditModal(false);
    } catch (err) {
      console.error("Error al editar:", err.message);
    }
  };

  return (
    <>
      <div className="movie-card rounded-4 card m-0 p-0 col-md-4 col-xl-2 shadow-sm">
        <IfRole roles={[CONSTANTS.ROLE_ADMIN, CONSTANTS.ROLE_DEV]}>
          <div className="d-flex m-0 p-0 position-absolute top-0 end-0" style={{ zIndex: 10 }}>
            <button className="btn btn-primary edit-button" onClick={() => setEditModal(true)}>
              <FontAwesomeIcon icon={faEdit} />
            </button>
            <button className="btn btn-danger delete-button" onClick={() => setDeleteTarget(movie_id)}>
              <FontAwesomeIcon icon={faTrash} />
            </button>
          </div>
        </IfRole>

        <img 
            src={cover} 
            alt={title} 
            onClick={() => setModal(true)} 
            className={authStatus === "authenticated" ? `rounded-top-4` : `rounded-4`} 
            style={{ cursor: 'pointer' }} 
        />

        <IfRole roles={[CONSTANTS.ROLE_USER, CONSTANTS.ROLE_ADMIN, CONSTANTS.ROLE_DEV]} >
          <div className="card-footer movie-vote rounded-bottom-4">
            <div className="d-flex align-items-center justify-content-center gap-3">
                <span onClick={() => handleVoteAction(1)} className={`vote-button ${userVote === 1 ? 'active-up' : ''}`}>
                  <FontAwesomeIcon icon={faThumbsUp} />
                </span>

                <small className={`fw-bold ${userVote === 1 ? 'text-warning' : userVote === -1 ? 'text-info' : ''}`}>
                    {totalScore}
                </small>

                <span onClick={() => handleVoteAction(-1)} className={`vote-button ${userVote === -1 ? 'active-down' : ''}`}>
                  <FontAwesomeIcon icon={faThumbsDown} />
                </span>
            </div>
          </div>
        </IfRole >
      </div>

      <CustomModal show={modal} onClose={() => setModal(false)} title={title}>
        <div className="p-3"><p>{description}</p></div>
      </CustomModal>

      <CustomModal show={editModal} onClose={() => setEditModal(false)} title="Editar Película">
        <EditMovieForm initialData={{ title, description, cover }} onSubmit={handleEdit} onCancel={() => setEditModal(false)} />
      </CustomModal>

      <CustomModal show={deleteTarget !== null} onClose={() => setDeleteTarget(null)} title="Eliminar">
        <div className="p-3">
          <p>¿Seguro que quieres borrar <b>{title}</b>?</p>
          <Button variant="danger" className="w-100" onClick={async () => {
            await deleteData(`${config.apiConfig.baseUrl}/movies/${movie_id}`);
            window.location.reload();
          }}>Borrar definitivamente</Button>
        </div>
      </CustomModal>
    </>
  );
};

export default MovieCard;