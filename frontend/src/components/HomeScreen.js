import React, { useState, useEffect } from 'react';
import TaskList from './TaskList';
import TaskForm from './TaskForm';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const HomeScreen = () => {
  const [tasks, setTasks] = useState(null);
  const [currentTask, setCurrentTask] = useState(null);
  const navigate = useNavigate();

  const handleTaskClick = (task) => {
    setCurrentTask(task);
  };

  useEffect(() => {
    console.log(sessionStorage.getItem('email'));
    axios.get(`http://localhost:8080/tasks/${sessionStorage.getItem('email')}`)
    .then(response => {
      setTasks(response.data)
    }).catch(error =>{
      console.error(error);
    });
  }, [currentTask]);

  const handleAddTask = () =>{
    setCurrentTask(null);
  };

  const handleLogout = () =>{
      sessionStorage.removeItem('email')
      navigate('/')
    };

  return (
    <div className="app-container">
      <div className="left-panel">
      <div className="header">
        <h1>TASK MANAGER</h1>
      </div>
      <div>
        <button className="button" onClick={handleAddTask}>
          Add Task
        </button>
        <button className="button-logout" onClick={handleLogout}>
        Logout
        </button>
        </div>
        <TaskList tasks={tasks} onTaskClick={handleTaskClick} />
      </div>
      <div className="right-panel">
        <TaskForm 
          currentTask={currentTask} 
          setCurrentTask={setCurrentTask}
        />
      </div>
    </div>
  );
};

export default HomeScreen;
