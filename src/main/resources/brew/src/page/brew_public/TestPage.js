import React from 'react';
import '../../style/App.css';
import TestComponent from "../../component/brew_public/test/TestComponent";
import HeaderComponent from "../../component/brew_common/HeaderComponent";

function TestPage() {
    return (
        <>
            <HeaderComponent />
            <TestComponent />
        </>
    );
}

export default TestPage;
