import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import TestPage from './page/brew_public/TestPage.js';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/api/temp" element={<TestPage />} />
                {/* 다른 라우트들을 추가할 수 있습니다. */}
            </Routes>
        </Router>
    );
}

export default App;