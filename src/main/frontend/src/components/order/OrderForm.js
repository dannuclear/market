import { Grid, TextField } from '@mui/material'
import React, { useEffect, useState } from 'react'
import { AutocompleteElement, FormContainer, TextFieldElement, useForm } from 'react-hook-form-mui'
import axios from '../../api/axios'

function OrderForm({
    formId,
    order,
    onSave = data => console.log(data),
    onError = () => console.log('Ошибка при сохранении формы'),
    disabled = false,
    formRef = ref => ref
}) {

    const [accountOptions, setAccountOptions] = useState([])

    const { reset, watch, setValue, formState: { errors }, ...formContext } = useForm({
        defaultValues: {
            ...order
        }
    })

    useEffect(() => {
        axios.get(`accounts`)
            .then(resp => setAccountOptions(resp.data?.content ?? []))
            .catch(err => err)
    }, [])

    const onSubmit = data => {
        onSave(data)
    }
    const [count, price, lot] = watch(["count", "price", "lot"])

    return (
        <FormContainer
            formContext={formContext}
            onSuccess={onSubmit}
            onError={onError}
            FormProps={{ id: formId, ref: formRef }}>
            <Grid container spacing={2} style={{ marginTop: 5 }}>

                <Grid item xs={12}>
                    <TextFieldElement
                        name='instrumentId'
                        label='Инструмент'
                        fullWidth={true} />
                </Grid>

                <Grid item xs={12}>
                    <AutocompleteElement
                        name='account'
                        label='Счет'
                        fullWidth={true}
                        rules={{ required: 'Счет обязателен' }}
                        options={accountOptions}
                        autocompleteProps={{
                            getOptionLabel: option => option.name
                        }}

                    ></AutocompleteElement>
                </Grid>

                <Grid item xs={3}>
                    <TextFieldElement
                        name='count'
                        label='Количество'
                        fullWidth={true}
                        validation={{ required: 'Количество не может быть пустым' }} />
                </Grid>

                <Grid item xs={3}>
                    <TextFieldElement
                        name='price'
                        label='Цена'
                        fullWidth={true}
                        validation={{ required: 'Цена не может быть пустым' }} />
                </Grid>

                <Grid item xs={3}>
                    <TextField label='Лот' fullWidth={true} value={lot}/>
                </Grid>

                <Grid item xs={3}>
                    <TextField label='Цена' fullWidth={true} value={(count??0) * (price??0) * (lot??0)}/>
                </Grid>
            </Grid>
        </FormContainer>
    )
}

export default OrderForm