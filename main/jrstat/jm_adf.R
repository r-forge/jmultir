jm_adf <- function(y, lags, seasDum=NULL, state=1){
  
  N <- length(y)
  deltay <- matrix(c(NA, diff(y, lag = 1)), ncol = 1)
  intercept <- matrix(rep(1, N), ncol = 1)
  trend <- matrix(c(rep(NA,lags+1),seq(-floor((N-lags-1)/2),by=1,length.out=(N-lags-1))), ncol = 1)
  
  detreg <- NULL
  if (state==2) detreg <- intercept
  if (state==3) detreg <- cbind(intercept,trend)
  if (state==4) detreg <- cbind(intercept,trend,seasDum)
  if (state==5) detreg <- cbind(intercept,seasDum)
  
  y_1 <- lagn(y,1)
  yreg <- lagn(deltay, lags)
  
  if (sum(state == 2:5) == 1){
    ifelse((lags > 0), lmadf <- lm(deltay[, 1] ~ 0 + y_1 + yreg + detreg),
           lmadf <- lm(deltay[, 1] ~ 0 + y_1 + detreg))
  }
  else{
    ifelse((lags > 0),  lmadf <- lm(deltay[, 1] ~ 0 + y_1 + yreg),
           lmadf <- lm(deltay[, 1] ~ 0 + y_1))
  }
  sum <- summary(lmadf)
  coeff_t <- sum$coeff[,c(1,3)]
  coeff_t <- rbind(coeff_t, c(sum $r.squared,NA))
  res <- lmadf$res
  return(list("est"=coeff_t, "resid"=res))
}
