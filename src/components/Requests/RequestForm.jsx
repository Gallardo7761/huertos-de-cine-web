import { useState } from 'react';
import { Form, Button, Alert } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faAt, faLock, faPaperPlane, faCancel } from '@fortawesome/free-solid-svg-icons';

const RequestForm = ({ onSubmit, onCancel }) => {
  const [displayName, setDisplayName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = [];
    
    if (!displayName.trim()) validationErrors.push("El nombre a mostrar es obligatorio.");
    if (!username.trim()) validationErrors.push("Tienes que ponerte un nombre de usuario.");
    if (!password.trim() || password.length < 6) validationErrors.push("La contraseña debe tener mínimo 6 caracteres, miarma.");

    if (validationErrors.length > 0) {
      setErrors(validationErrors);
      return;
    }

    setErrors(null);
    onSubmit({ displayName, username, password, status: 0 });
  };

  return (
    <div className="p-3">
      <Form onSubmit={handleSubmit}>
        {errors && (
          <Alert variant="danger" className="rounded-4">
            <ul className="mb-0">
              {errors.map((err, idx) => <li key={idx}>{err}</li>)}
            </ul>
          </Alert>
        )}

        <Form.Group className="mb-3">
          <Form.Label className="fw-bold">
            <FontAwesomeIcon icon={faUser} className="me-2 text-secondary" /> Nombre a mostrar
          </Form.Label>
          <Form.Control
            type="text"
            value={displayName}
            onChange={(e) => setDisplayName(e.target.value)}
            className="rounded-4 themed-input"
            placeholder="Ej: Pepe García"
          />
        </Form.Group>

        <Form.Group className="mb-3">
          <Form.Label className="fw-bold">
            <FontAwesomeIcon icon={faAt} className="me-2 text-secondary" /> Nombre de usuario
          </Form.Label>
          <Form.Control
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="rounded-4 themed-input"
            placeholder="pgarcía"
          />
        </Form.Group>

        <Form.Group className="mb-4">
          <Form.Label className="fw-bold">
            <FontAwesomeIcon icon={faLock} className="me-2 text-secondary" /> Contraseña
          </Form.Label>
          <Form.Control
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="rounded-4 themed-input"
            placeholder="Mínimo 6 caracteres"
          />
        </Form.Group>

        <div className="d-flex justify-content-end gap-2 mt-2">
          <Button variant="outline-danger" onClick={onCancel} className="rounded-4 px-4">
            <FontAwesomeIcon icon={faCancel} className="me-2" /> Cancelar
          </Button>
          <Button variant="primary" type="submit" className="rounded-4 px-4 shadow-sm">
            <FontAwesomeIcon icon={faPaperPlane} className="me-2" /> Enviar Solicitud
          </Button>
        </div>
      </Form>
    </div>
  );
};

export default RequestForm;