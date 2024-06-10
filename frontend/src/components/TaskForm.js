import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../Styles/style.css';

const TaskForm = ({ currentTask, setCurrentTask }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [status, setStatus] = useState('TO DO');
  const [dueDate, setDueDate] = useState('');
  const [priority, setPriority] = useState('LOW');
  const [id, setID] = useState('');

  useEffect(() => {
    if (currentTask) {
      setTitle(currentTask.title);
      setDescription(currentTask.description);
      setStatus(currentTask.status);
      setDueDate(formatDate(currentTask.dueDate));
      setPriority(currentTask.priority);
      setID(currentTask.id);
    } else {
      setTitle('');
      setDescription('');
      setStatus('TO DO');
      setDueDate('');
      setPriority('LOW');
    }
  }, [currentTask]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (currentTask) {
      const task = { id, title, description, status, dueDate, priority };
      axios.put(`http://localhost:8080/tasks/${currentTask.id}`, task)
      .then(response => {
        setCurrentTask(null);
      });
    } else {
      const task = { title, description, status, dueDate, priority };
      axios.post('http://localhost:8080/tasks', task)
      .then(response => {
        setCurrentTask(response.data);
      });
    }
  };

  const handleDelete = (e) => {
    e.preventDefault();
    axios.delete(`http://localhost:8080/tasks/${currentTask.id}`)
    .then(response => {
      setCurrentTask(null);
    });
  };

  const formatDate = (isoDate) => {
    const date = new Date(isoDate);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  return (
    <form className="form" onSubmit={handleSubmit}>
      <input 
        type="text" 
        className="input" 
        placeholder="Task Title" 
        value={title} 
        onChange={(e) => setTitle(e.target.value)} 
      />
      <input 
        type="text" 
        className="input" 
        placeholder="Task Description" 
        value={description} 
        onChange={(e) => setDescription(e.target.value)} 
      />
      <select 
        className="select" 
        value={status} 
        onChange={(e) => setStatus(e.target.value)}
      >
        <option value="TO DO">TO DO</option>
        <option value="IN PROGRESS">IN PROGRESS</option>
        <option value="DONE">DONE</option>
      </select>
      <input 
        type="date" 
        className="input" 
        value={dueDate} 
        onChange={(e) => setDueDate(e.target.value)} 
      />
      <select 
        className="select" 
        value={priority} 
        onChange={(e) => setPriority(e.target.value)}
      >
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
      </select>
      <button type="submit" className="button">
        {currentTask ? 'Update Task' : 'Add Task'}
      </button>
      {currentTask && (
        <button className="button delete-button" onClick={handleDelete}>
          Delete Task
        </button>
      )}
    </form>
  );
};

export default TaskForm;