import React, { useState } from 'react';
import '../Styles/SignUp.css';
import { useNavigate } from 'react-router-dom';
import { ThreeDots } from 'react-loader-spinner';


const SignUp = () => {
  const [fname, setFName] = useState('');
  const [lname, setLName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confpassword, setConfPassword] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const serverURL = 'http://localhost:8080';

  const handleRegister = async () => {
    if (!isValidEmail(email)) {
      setMessage('Please enter a valid email');
      return;
    }
    else if( !password || !lname || !email || !fname){
      setMessage('Enter all the fields');
    }
    else if (confpassword!==password){
      setMessage('Password and Confirmned Password Mismatch');
    }
    else{
        setLoading(true);
          fetch(`${serverURL}/users`, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({ fname,lname, email, password }),
          })
          .then(response => {
                if (!response.ok) {
                  setMessage('Could not Signup. Please try again')
                  return;
                }
                return response.json(); // Parse the JSON response
              })
              .then(data => {
                  sessionStorage.setItem('email', data.email);
                  navigate('/home')
              })
          .catch(error => {
            console.error('Error:', error);
          });
          setLoading(false);
    }
    };

    const isValidEmail = (email) => {
      // Simple email format validation
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    };

  return (
    <div className="wrapper">
    <form>
    <h2>Registration</h2>
      <div className="input-box">
        <input type="text" onChange={(e) => setFName(e.target.value)} placeholder="Enter your First name" required/>
      </div>
      <div className="input-box">
        <input type="text" onChange={(e) => setLName(e.target.value)} placeholder="Enter your Last name" required/>
      </div>
      <div className="input-box">
        <input type="text"  onChange={(e) => setEmail(e.target.value)} placeholder="Enter your email" required/>
      </div>
      <div className="input-box">
        <input type="password" onChange={(e) => setPassword(e.target.value)} placeholder="Create password" required/>
      </div>
      <div className="input-box">
        <input type="password" onChange={(e) => setConfPassword(e.target.value)} placeholder="Confirm password" required/>
      </div>
      <div className="input-box-button">
      {loading ? (
             <ThreeDots color="#00BFFF" height={24} width={24} />
          ) : (
        <input onClick={handleRegister} defaultValue="Register Now"/>
        )}
      </div>
      {message && <p style={{ whiteSpace: 'pre-wrap', marginBottom: '0', color:'red', fontWeight: 'bold' }} >{message}</p>}
      </form>
  </div>
  );
};

export default SignUp;
