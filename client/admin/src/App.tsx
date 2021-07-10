import './App.css';
import React from 'react'
import { Admin, fetchUtils, Resource } from 'react-admin'
import simpleRestProvider from 'ra-data-simple-rest';
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
import SaleCreate from './components/sale/SaleCreate';
import SaleEdit from './components/sale/SaleEdit';
import SaleList from './components/sale/SaleList';
import SaleProductsCreate from './components/saleproducts/SaleProductsCreate';
import SaleProductsEdit from './components/saleproducts/SaleProductsEdit';
import SaleProductsList from './components/saleproducts/SaleProductsList';
import OptionCreate from './components/option/OptionCreate';
import OptionEdit from './components/option/OptionEdit';
import OptionList from './components/option/OptionList';
import ReportList from './components/report/ReportList';
import authProvider from './auth/EfimeroAuthProvider';
//import Dash from './Dash';

/*const httpClient = (url: any, options: any) => {
    if (!options.headers) {
        options.headers = new Headers({ Accept: 'application/json' });
    }
    let ls = localStorage.getItem('auth');
    if (ls != null) {
        const { token } = JSON.parse(ls);
        options.headers.set('Authorization', `Bearer ${token}`);
    }
    return fetchUtils.fetchJson(url, options);
};*/

function App() {
    let apiUrl = process.env.NODE_ENV === 'development' ? process.env.REACT_APP_API_URL_DEV : process.env.REACT_APP_API_URL_PROD;
    if (apiUrl === undefined)
        apiUrl = 'REACT_APP_API_URL undefined'
    console.log(apiUrl);

    //const dataProvider = simpleRestProvider(apiUrl, httpClient, 'X-Total-Count');
    const dataProvider = simpleRestProvider(apiUrl, fetchUtils.fetchJson, 'X-Total-Count');
    
    // TODO add auth: authProvider={authProvider}
    return (
        <Admin dataProvider={dataProvider}>
            <Resource
                name='api/v1/products'
                options={{ label: 'Productos' }}
                list={ProdList}
                create={ProdCreate}
                edit={ProdEdit}
            />
            <Resource
                name='api/v1/stock'
                options={{ label: 'Stock' }}
                list={StockList}
                create={StockCreate}
                edit={StockEdit}
            />
            <Resource
                name='api/v1/sales'
                options={{ label: 'Ventas' }}
                list={SaleList}
                create={SaleCreate}
                edit={SaleEdit}
            />
            <Resource
                name='api/v1/users'
                options={{ label: 'Usuarios' }}
                list={UserList}
                create={UserCreate}
                edit={UserEdit}
            />
            <Resource
                name='api/v1/categories'
                options={{ label: 'Categorías' }}
                list={CatList}
                create={CatCreate}
                edit={CatEdit}
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
                name='api/v1/saleproducts'
                options={{ label: 'SaleProducts' }}
                list={SaleProductsList}
                create={SaleProductsCreate}
                edit={SaleProductsEdit}
            />
            <Resource
                name='api/v1/options'
                options={{ label: 'Opciones' }}
                list={OptionList}
                create={OptionCreate}
                edit={OptionEdit}
            />
            <Resource
                name='api/v1/report'
                options={{ label: 'Reporte' }}
                list={ReportList}
            />
            <Resource name='api/v1/options/type/paymentMethod' />
            <Resource name='api/v1/options/type/price' />
            <Resource name='api/v1/options/type/rol' />
        </Admin>
    );
}

export default App;
