import Header from "@/components/Header";
import { Route, Routes, Navigate, Link } from 'react-router-dom'
import Login from "@/pages/Login";
import Votar from "@/pages/Votar";
import NotFound from "@/pages/NotFound";
import Footer from "@/components/Footer";
import ProtectedRoute from "@/components/Auth/ProtectedRoute";
import { CONSTANTS } from "@/util/constants";
import Admin from "@/pages/Admin";

const App = () => {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<Votar />} />
        <Route path="/login" element={<Login />} />
        <Route path="/admin" element={
          <ProtectedRoute minimumRoles={[CONSTANTS.ROLE_ADMIN, CONSTANTS.ROLE_DEV]}>
            <Admin />
          </ProtectedRoute>
        } />
        <Route path="/*" element={<NotFound />} />
      </Routes>
      <Footer />
    </>
  )
}

export default App;
