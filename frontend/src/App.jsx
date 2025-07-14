import { useState, useEffect } from "react";

const API = "http://localhost:8089/react/todos";

function App() {
  const [todos, setTodos] = useState([]);
  const [title, setTitle] = useState("");

  useEffect(() => {
    fetch(API)
      .then((res) => res.json())
      .then((data) => setTodos(data))
      .catch((err) => console.error("error", err));
  }, []);

  const addTodo = async () => {
    if (!title.trim()) return;

    const res = await fetch(API, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ task: title }),
    });

    const newTodo = await res.json();
    setTodos([...todos, newTodo]);
    setTitle("");
  };

  return (
    <div className="container mt-4" style={{ maxWidth: "500px" }}>
      <div className="card shadow">
        <div className="card-body">
          <h1 className="card-title text-center mb-4">📋 Todo List</h1>
          <div className="input-group mb-3">
            <input
              type="text"
              className="form-control"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              onKeyDown={(e) =>{
                if(e.key === "Enter") addTodo();
              }}
              placeholder="新增代辦事項"
            />

            <button onClick={addTodo} className="btn btn-primary">新增</button>
          </div>
          <ul className="list-group">
            {todos.map((todo) => (
              <li key={todo.id} className="list-group-item">{todo.task}</li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}

export default App;
