import styled from '@emotion/styled';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material'
import { lightGreen } from '@mui/material/colors'
import React from 'react'

const CustomizedDialog = styled(Dialog)(({ theme }) => ({
    '& .MuiDialogTitle-root': {
        padding: theme.spacing(1),
        background: theme.palette.primary.main,
        color: 'white'
    },
    '& .MuiDialogContent-root': {
        padding: `${theme.spacing(1)}!important`,
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1),
        background: theme.palette.primary.main,
        '& .MuiButtonBase-root': {
            color: 'white',
            border: '2px solid rgba(255, 255, 255, 0.5)',
        }
    },
}));

const AgreeDisagreeDialog = ({
    title = 'Диалог',
    maxWidth = 'md',
    minHeight,
    open = false,
    onAgree = () => console.log('Нажато согласие'),
    onDisagree = () => console.log('Нажато отмена'),
    disagreeButtonLabel = 'Отмена',
    agreeButtonLabel = 'Ок',
    form,
    content,
    renderDisagreeButton = true
}) => {
    return (
        <CustomizedDialog
            open={open}
            fullWidth={true}
            maxWidth={maxWidth}
        >
            <DialogTitle variant='h6' sx={{ background: lightGreen[100] }}>
                {title}
            </DialogTitle>
            <DialogContent sx={{ minHeight: { minHeight } }}>
                {content}
            </DialogContent>
            <DialogActions sx={{ background: lightGreen[100] }}>
                {renderDisagreeButton && <Button variant='outlined' onClick={onDisagree}>{disagreeButtonLabel}</Button>}
                <Button form={form} type={form ? 'submit' : 'button'} variant='outlined' onClick={onAgree}>{agreeButtonLabel}</Button>
            </DialogActions>
        </CustomizedDialog>
    )
}

export default AgreeDisagreeDialog