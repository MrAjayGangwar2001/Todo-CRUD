import axios from "../api/api";
import { useState } from "react";

function Signup({ setShowSignup, onSignupSuccess }) {

    const [form, setForm] = useState({
        userName: "",
        email: "",
        password: "",
        confirmPassword: ""
    });

    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!form.userName || !form.email || !form.password || !form.confirmPassword) {
            setError("All fields are required");
            return;
        }
        if (form.password.length < 6) {
            setError("Password must be at least 6 characters");
            return;
        }
        if (form.password !== form.confirmPassword) {
            setError("Password & Confirm Password do not match");
            return;
        }

        setLoading(true);
        setError("");

        try {
            const res = await axios.post("/auth/signup", {
                userName: form.userName,
                email: form.email,
                password: form.password
            });

            localStorage.setItem("user", JSON.stringify(res.data));
            onSignupSuccess();
        } catch (err) {
            setError(err.response?.data?.message || "Signup failed");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container">
            <div className="card">
                <h3 className="text-center mb-3">Signup</h3>

                {error && (
                    <div className="alert alert-danger alert-dismissible">
                        {error}
                        <button className="btn-close" onClick={() => setError("")} />
                    </div>
                )}

                <form onSubmit={handleSubmit}>
                    <input
                        className="form-control mb-2"
                        placeholder="Username"
                        onChange={e => setForm({ ...form, userName: e.target.value })}
                    />
                    <input
                        type="email"
                        className="form-control mb-2"
                        placeholder="Email"
                        onChange={e => setForm({ ...form, email: e.target.value })}
                    />
                    <input
                        type="password"
                        className="form-control mb-2"
                        placeholder="Password"
                        onChange={e => setForm({ ...form, password: e.target.value })}
                    />
                    <input
                        type="password"
                        className="form-control mb-3"
                        placeholder="Confirm Password"
                        onChange={e => setForm({ ...form, confirmPassword: e.target.value })}
                    />
                    <button
                        className="btn btn-primary w-100"
                        disabled={loading}
                    >
                        {loading ? "Signing up..." : "Signup"}
                    </button>
                </form>
            </div>
        </div>
    );
}

export default Signup;