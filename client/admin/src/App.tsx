import './App.css';
import React from 'react'
import { Admin, Resource, fetchUtils } from 'react-admin'
import simpleRestProvider from 'ra-data-simple-rest';
import PostCreate from "./components/post/PostCreate";
import PostList from "./components/post/PostList";
import PostEdit from "./components/post/PostEdit";
import CatCreate from './components/category/CatCreate';
import CatList from './components/category/CatList';
import CatEdit from './components/category/CatEdit';
import ProdCreate from './components/product/ProdCreate';
import ProdList from './components/product/ProdList';
import ProdEdit from './components/product/ProdEdit';
import UserCreate from './components/user/UserCreate';
import UserList from './components/user/UserList';
import UserEdit from './components/user/UserEdit';
import ColorList from './components/color/ColorList';
import ColorCreate from './components/color/ColorCreate';
import ColorEdit from './components/color/ColorEdit';
import PriceCreate from './components/price/PriceCreate';
import PriceEdit from './components/price/PriceEdit';
import PriceList from './components/price/PriceList';
import StockCreate from './components/stock/StockCreate';
import StockEdit from './components/stock/StockEdit';
import StockList from './components/stock/StockList';
import { Dashboard } from './dashboard';

function App() {
    return (
        <Admin dashboard={Dashboard} dataProvider={simpleRestProvider('http://localhost:8080', fetchUtils.fetchJson, 'X-Total-Count')}>
            <Resource
                name='api/v1/departments'
                options={{ label: 'Departamentos' }}
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
            <Resource
                name='api/v1/products'
                options={{ label: 'Productos' }}
                list={ProdList}
                create={ProdCreate}
                edit={ProdEdit}
            />
            <Resource
                name='api/v1/users'
                options={{ label: 'Usuarios' }}
                list={UserList}
                create={UserCreate}
                edit={UserEdit}
            />
            <Resource
                name='api/v1/colors'
                options={{ label: 'Colores' }}
                list={ColorList}
                create={ColorCreate}
                edit={ColorEdit}
            />
            <Resource
                name='api/v1/prices'
                options={{ label: 'Precios' }}
                list={PriceList}
                create={PriceCreate}
                edit={PriceEdit}
            />
            <Resource
                name='api/v1/stock'
                options={{ label: 'Stock' }}
                list={StockList}
                create={StockCreate}
                edit={StockEdit}
            />
        </Admin>
    );
}

export default App;
