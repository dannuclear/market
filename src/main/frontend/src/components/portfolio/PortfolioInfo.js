import { Grid, Typography } from '@mui/material'
import React from 'react'

export default function PortfolioInfo({
    portfolio
}) {
    return (portfolio &&
        <Grid container spacing={2} style={{ marginTop: 5 }}>
            <Grid item xs={2}>
                <Typography>ID:</Typography>
            </Grid>
            <Grid item xs={10}>
                <Typography>{portfolio?.accountId}</Typography>
            </Grid>

            <Grid item xs={10}>
                <Typography>Общая стоимость акций в портфеле</Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography>{portfolio?.totalAmountShares?.value} {portfolio?.totalAmountShares?.currency}</Typography>
            </Grid>

            <Grid item xs={10}>
                <Typography>Общая стоимость облигаций в портфеле</Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography>{portfolio?.totalAmountBonds?.value} {portfolio?.totalAmountBonds?.currency}</Typography>
            </Grid>

            <Grid item xs={10}>
                <Typography>Общая стоимость фондов в портфеле</Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography>{portfolio?.totalAmountEtfs?.value} {portfolio?.totalAmountEtfs?.currency}</Typography>
            </Grid>

            <Grid item xs={10}>
                <Typography>Общая стоимость валют в портфеле</Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography>{portfolio?.totalAmountCurrencies?.value} {portfolio?.totalAmountCurrencies?.currency}</Typography>
            </Grid>

            <Grid item xs={10}>
                <Typography>Общая стоимость фьючерсов в портфеле</Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography>{portfolio?.totalAmountFutures?.value} {portfolio?.totalAmountFutures?.currency}</Typography>
            </Grid>

            <Grid item xs={10}>
                <Typography>Текущая относительная доходность портфеля</Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography>{portfolio?.expectedYield} %</Typography>
            </Grid>

            <Grid item xs={10}>
                <Typography>Общая стоимость опционов</Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography>{portfolio?.totalAmountOptions?.value} {portfolio?.totalAmountOptions?.currency}</Typography>
            </Grid>

            <Grid item xs={10}>
                <Typography>Общая стоимость структурных нот</Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography>{portfolio?.totalAmountSp?.value} {portfolio?.totalAmountSp?.currency}</Typography>
            </Grid>

            <Grid item xs={10}>
                <Typography>Общая стоимость портфеля</Typography>
            </Grid>
            <Grid item xs={2}>
                <Typography>{portfolio?.totalAmountPortfolio?.value} {portfolio?.totalAmountPortfolio?.currency}</Typography>
            </Grid>
        </Grid>
    )
}

{/* 
  repeated PortfolioPosition positions = 7; //Список позиций портфеля.
  repeated VirtualPortfolioPosition virtual_positions = 12; //Массив виртуальных позиций портфеля. */}