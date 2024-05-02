import { Grid } from '@mui/material'
import React, { useEffect } from 'react'
import { useForm } from 'react-hook-form'
import { FormContainer, TextFieldElement } from 'react-hook-form-mui'

function AccountForm({
    formId,
    account,
    onSave = data => console.log(data),
    onError = () => console.log('Ошибка при сохранении формы'),
    disabled = false,
    formRef = ref => ref
}) {
    const { reset, watch, setValue, formState: { errors }, ...formContext} = useForm({
        defaultValues: account && {
            ...account
        }
    })

    const onSubmit = data => {
        onSave(data)
    }

    return (
        <FormContainer
            formContext={formContext}
            onSuccess={onSubmit}
            onError={onError}
            FormProps={{ id: formId, ref: formRef }}>
            <Grid container spacing={2} style={{ marginTop: 5 }}>
                <Grid item xs={12}>
                    <TextFieldElement
                        name='name'
                        label='Наименование'
                        fullWidth={true}
                        validation={{ required: 'Наименование не может быть пустым' }} />
                </Grid>
            </Grid>
        </FormContainer>
    )
}

export default AccountForm