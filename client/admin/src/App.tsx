import './App.css';
import React from 'react'
import {Admin, Resource} from 'react-admin'
import restProvider from 'ra-data-simple-rest'
import PostCreate from "./components/post/PostCreate";
import PostList from "./components/post/PostList";
import PostEdit from "./components/post/PostEdit";
import CatCreate from './components/category/CatCreate';
import CatList from './components/category/CatList';
import CatEdit from './components/category/CatEdit';

function App() {
    return (
        <Admin dataProvider={restProvider('http://localhost:3000')}>
            <Resource
                name='api/v1/departments'
                list={PostList}
                create={PostCreate}
                edit={PostEdit}
            />
            <Resource
                name='api/v1/categories'
                options={{ label: 'Categorías' }}
                list={CatList}
                create={CatCreate}
                edit={CatEdit}
            />
        </Admin>
    );
}

export default App;
