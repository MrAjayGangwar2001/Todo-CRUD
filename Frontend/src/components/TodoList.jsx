import axios from "../api/api";
import { useEffect, useState } from "react";

export default function TodoList() {
    const [todos, setTodos] = useState([]);
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [editTodo, setEditTodo] = useState(null);
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const [loading, setLoading] = useState(false);

    const loadTodos = async () => {
        try {
            const res = await axios.get("/todo/allTodo");
            setTodos(res.data);
        } catch (err) {
            console.error("Load todos error:", err.response);
            setError(err.response?.data?.message || "Failed to load todos");
        }
    };

    useEffect(() => {
        // Token check karo pehle
        const userData = localStorage.getItem("user");
        console.log("Stored user data:", userData); // ← Debug
        loadTodos();
    }, []);

    const saveTodo = async () => {
        if (!title || !description) return;

        setLoading(true);
        setError("");
        setSuccess("");

        try {
            if (editTodo) {
                await axios.patch(`/todo/${editTodo}`, { title, description });
                setSuccess("Todo updated successfully!");
            } else {
                await axios.post("/todo/create", { title, description });
                setSuccess("Todo created successfully!");
            }
            setTitle("");
            setDescription("");
            setEditTodo(null);
            loadTodos();
        } catch (err) {
            console.error("Save todo error:", err.response); // ← Debug
            setError(err.response?.data?.message || "Operation failed");
        } finally {
            setLoading(false);
        }
    };

    const handleEdit = (todo) => {
        setEditTodo(todo.id);
        setTitle(todo.title);
        setDescription(todo.description);
        setError("");
        setSuccess("");
    };

    const deleteTodo = async (id) => {
        setError("");
        setSuccess("");
        try {
            await axios.delete(`/todo/${id}`);
            setSuccess("Todo deleted!");
            loadTodos();
        } catch (err) {
            console.error("Delete error:", err.response); // ← Debug
            setError(err.response?.data?.message || "Delete failed");
        }
    };

    return (
        <div className="container mt-3">
            <div className="row justify-content-center">
                <div className="col-md-12">
                    <div className="card shadow-sm">
                        <div className="card-header bg-primary text-white text-center">
                            <h4 className="mb-0">My Todo List</h4>
                        </div>

                        <div className="card-body">

                            {/* Error & Success Messages */}
                            {error && (
                                <div className="alert alert-danger alert-dismissible">
                                    {error}
                                    <button
                                        className="btn-close"
                                        onClick={() => setError("")}
                                    />
                                </div>
                            )}
                            {success && (
                                <div className="alert alert-success alert-dismissible">
                                    {success}
                                    <button
                                        className="btn-close"
                                        onClick={() => setSuccess("")}
                                    />
                                </div>
                            )}

                            {/* Add / Edit Form */}
                            <div className="mb-4">
                                <input
                                    className="form-control mb-2"
                                    placeholder="Todo Title"
                                    value={title}
                                    onChange={e => setTitle(e.target.value)}
                                />
                                <textarea
                                    className="form-control mb-3"
                                    placeholder="Todo Description"
                                    rows="3"
                                    value={description}
                                    onChange={e => setDescription(e.target.value)}
                                />
                                <button
                                    className={`btn ${editTodo ? "btn-warning" : "btn-success"} w-100`}
                                    onClick={saveTodo}
                                    disabled={!title || !description || loading}
                                >
                                    {loading
                                        ? "Processing..."
                                        : editTodo
                                            ? "✏️ Update Todo"
                                            : "➕ Add Todo"}
                                </button>

                                {/* Cancel edit button */}
                                {editTodo && (
                                    <button
                                        className="btn btn-secondary w-100 mt-2"
                                        onClick={() => {
                                            setEditTodo(null);
                                            setTitle("");
                                            setDescription("");
                                        }}
                                    >
                                        ✖ Cancel Edit
                                    </button>
                                )}
                            </div>

                            {/* Todo List */}
                            {todos.length === 0 ? (
                                <p className="text-center text-muted">
                                    No todos available. Add one!
                                </p>
                            ) : (
                                <ul className="list-group">
                                    {todos.map(t => (
                                        <li
                                            key={t.id}
                                            className="list-group-item d-flex justify-content-between align-items-start"
                                        >
                                            <div>
                                                <h6 className="mb-1">{t.title}</h6>
                                                <small className="text-muted">
                                                    {t.description}
                                                </small>
                                            </div>
                                            <div className="d-flex gap-2">
                                                <button
                                                    className="btn btn-outline-primary btn-sm"
                                                    onClick={() => handleEdit(t)}
                                                >
                                                    Edit
                                                </button>
                                                <button
                                                    className="btn btn-outline-danger btn-sm"
                                                    onClick={() => deleteTodo(t.id)}
                                                >
                                                    Delete
                                                </button>
                                            </div>
                                        </li>
                                    ))}
                                </ul>
                            )}
                        </div>

                        <h6 className="text-muted text-center pb-2">
                            Total Todos: {todos.length}
                        </h6>
                    </div>
                </div>
            </div>
        </div>
    );
}