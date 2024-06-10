import React, { useState, useEffect } from 'react';
import TaskList from './components/TaskList';
import TaskForm from './components/TaskForm';
import axios from 'axios';
import './App.css';

const App = () => {
  const [tasks, setTasks] = useState(null);
  const [currentTask, setCurrentTask] = useState(null);

  const handleTaskClick = (task) => {
    setCurrentTask(task);
  };

  useEffect(() => {
    axios.get('http://localhost:8080/tasks')
    .then(response => {
      setTasks(response.data)
    }).catch(error =>{
      console.error(error);
    });
  }, [currentTask]);

  const handleAddTask = () =>{
    setCurrentTask(null);
  };

  return (
    <div className="app-container">
      <div className="left-panel">
      <div className="header">
        <h1>TASK MANAGER</h1>
      </div>
        <button className="button" onClick={handleAddTask}>
          Add Task
        </button>
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

export default App;
