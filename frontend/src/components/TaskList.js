import React from 'react';
import '../Styles/style.css';

const TaskList = ({ tasks, onTaskClick }) => {
  if (!tasks) {
    return <h5>No Task Created</h5>
  }

  return (
    <ul className="list">
      {tasks && tasks.map(task => (
        <li key={task.id} className="list-item" onClick={() => onTaskClick(task)}>
          {task.title}
        </li>
      ))}
    </ul>
  );
};

export default TaskList;