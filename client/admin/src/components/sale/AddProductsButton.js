import React from 'react';
import { Link } from 'react-router-dom';
import ChatBubbleIcon from '@material-ui/icons/ChatBubble';
import { Button } from 'react-admin';

const AddProductsButton = ({ record }) => (
    <Button
        variant="raised"
        component={Link}
        to={`/api/v1/saleproducts/create?saleId=${record.id}`}
        label="Agregar Productos"
        title="Agregar Productos">
        <ChatBubbleIcon />
    </Button>
);

export default AddProductsButton;