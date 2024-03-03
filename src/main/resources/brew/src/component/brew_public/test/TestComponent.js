import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../../style/App.css';

function TestComponent() {

    // message 초기값 설정 (""로 설정)
    const [message, setMessage] = useState("");

    // useEffect(함수, 배열) : 컴포넌트가 화면에 나타났을 때 자동 실행
    useEffect(() => {
        // fetch(url, options) : Http 요청 함수
        fetch("/nowij")
            .then(response => response.text())
            .then(message => {
                setMessage(message);
            });
    }, [])

    const [data, setData] = useState([]);

    useEffect(() => {
        // Axios를 사용하여 Spring Boot 컨트롤러에서 데이터를 요청합니다.
        axios.get('/api/data')
            .then(response => {
                setData(response.data); // 받아온 데이터를 state에 설정합니다.
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    }, []); // 빈 배열을 전달하여 컴포넌트가 마운트될 때 한 번만 실행되도록 합니다.



    return (
        <>
            <div>테스트 컴포넌트</div>
            <div>
                <h1>Data from Spring Boot</h1>
                <ul>
                    {data.map(item => (
                        <li key={item}>{item}</li>
                    ))}
                </ul>
            </div>
            <p>
                nowij : {message}
            </p>
        </>
    );
}

export default TestComponent;