import axios from "../api/api";
import { useState } from "react";

function Login({ setLoggedIn }) {

    const [form, setForm] = useState({
        email: "",
        password: ""
    });

    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!form.email || !form.password) {
            setError("Email and Password are required");
            return;
        }

        try {
            const res = await axios.post("/auth/login", form); // ← /auth/login
            localStorage.setItem("user", JSON.stringify(res.data));
            setLoggedIn(true);
        } catch (err) {
            setError(err.response?.data?.message || "Invalid credentials");
        }
    };

    return (
        <div className="container">
            <div className="card">
                <h3 className="text-center mb-3">Login</h3>

                {error && <div className="alert alert-danger">{error}</div>}

                <form onSubmit={handleSubmit}>
                    <input
                        type="email"
                        className="form-control mb-2"
                        placeholder="Email"
                        value={form.email}
                        onChange={e => setForm({ ...form, email: e.target.value })}
                    />

                    <input
                        type="password"
                        className="form-control mb-3"
                        placeholder="Password"
                        value={form.password}
                        onChange={e => setForm({ ...form, password: e.target.value })}
                    />

                    <button className="btn btn-success w-100">
                        Login
                    </button>
                </form>
            </div>
        </div>
    );
}

export default Login;
