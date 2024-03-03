import React, {useEffect, useState} from 'react';
import axios from "axios";

function HeaderComponent() {

    // const [result, setResult] = useState([]);
    //
    // useEffect(() => {
    //     axios.get('/api/data')
    //         .then(response => {
    //             setResult(response.data);
    //         })
    //         .catch(error => {
    //             console.error('Error', error);
    //         });
    // }, []);

    return (
        <div style={{display:"flex", justifyContent:"space-between", width:"100vw"}}>
            <div style={{width:"20%"}}>
                <div>로고</div>
            </div>
            <div style={{width:"60%"}}>
                <div>
                    <ul style={{display:"flex", justifyContent:"space-around"}}>
                        <li>메뉴1</li>
                        <li>메뉴2</li>
                        <li>메뉴3</li>
                        <li>메뉴4</li>
                    </ul>
                </div>
            </div>
            <div style={{display:"flex", justifyContent:"space-around", width:"20%"}}>
                <div>사용자</div>
                <div><button>로그인</button></div>
                <div><button>APP</button></div>
            </div>
        </div>
    )
}

export default HeaderComponent;