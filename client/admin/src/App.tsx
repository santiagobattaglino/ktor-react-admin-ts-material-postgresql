import './App.css';
import React from 'react'
import {Admin, Resource} from 'react-admin'
import restProvider from 'ra-data-simple-rest'
import PostCreate from "./components/PostCreate";
import PostList from "./components/PostList";
import PostEdit from "./components/PostEdit";

function App() {
    return (
        <Admin dataProvider={restProvider('http://localhost:3000')}>
            <Resource
                name='api/v1/departments'
                list={PostList}
                create={PostCreate}
                edit={PostEdit}
            />
        </Admin>
    );
}

export default App;
