import React from 'react'
import { Edit, SimpleForm, TextInput, DateInput } from 'react-admin'

const PostEdit = (props: any) => {
    return (
        <Edit title='Edit Post' {...props}>
            <SimpleForm>
                <TextInput disabled source='id' />
            </SimpleForm>
        </Edit>
    )
}

export default PostEdit
