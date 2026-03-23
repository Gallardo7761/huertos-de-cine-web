import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'

/* COMPONENTS */
import App from './App.jsx'
import { BrowserRouter } from 'react-router-dom'
import { AuthProvider } from '@/context/AuthContext'
import { ConfigProvider } from '@/context/ConfigContext.jsx'

/* CSS */
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import '@/css/index.css'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <ConfigProvider>
      <AuthProvider>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </AuthProvider>
    </ConfigProvider>
  </StrictMode>,
)
