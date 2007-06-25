# see Franses (1996, p.63-65) or
#     Franses and Hobijn (1997, Journal of Applied Statistics, Vol 24, No 1, 25-47))
#
# state == 1: no const, no trend, no seasD 
# state == 2: const, no  trend, no seasD 
# state == 3: const, trend, no seasD 
# state == 4: const, trend, seasD 
# state == 5: const, no trend, seasD
# lags == number of lags of y4 = y(t)-y(t-4)
# y == Tx1 Data Matrix
#
# order of critVal Matrix: 1%,5%,10% for t(pi1), t(pi2), F34, F234, F1234
# procedure for quarterly data
#
#

jm_hegy4 <- function(y, seasDum=NULL, lags=1, state=1){
    
  T <- length(y)
 
  y1      <- y[4:T]+y[3:(T-1)]+y[2:(T-2)]+y[1:(T-3)]
  y2      <- -(y[4:T]-y[3:(T-1)]+y[2:(T-2)]-y[1:(T-3)])   	
  y3      <- -(y[3:T]-y[1:(T-2)])
  y4      <- y[5:T]-y[1:(T-4)]

  yright  <- cbind(y1[1:(length(y1)-1)], y2[1:(length(y2)-1)], y3[1:(length(y3)-2)], y3[2:(length(y3)-1)])
  lagNamesToAdd <- c()
  if (lags > 0){
    y4lags <- lagn(y4, lags)[(1+lags):length(y4),]
	l  <-  length(y4) - lags
    yright <- cbind(yright[(nrow(yright)-l+1) : nrow(yright), ], y4lags)
	lagNamesToAdd <- paste("X", 5:(5+lags-1) , sep="")
	lagNamesToAdd <- c("+", lagNamesToAdd)
  }
  Tnew <- nrow(yright)
  y4  <- y4[(1+length(y4)-Tnew):length(y4)]
  trend <- (T-Tnew+1):T
 
  if (length(seasDum)  > 0 )   {
    seasDum <- seasDum[(1+length(seasDum)-Tnew):length(seasDum)]
  }

  formulaExpression <- "y4 ~ "
 
  # no const, no trend, no seasD 
  if (state == 1){
    formulaExpression <- paste(formulaExpression, "0")
    critVal <- rbind( c(-2.54,-1.91,-1.59), c(-2.53,-1.93,-1.61), c(4.85,3.11,2.39), c(4.05,2.78,2.22), c(3.58,2.55,2.09))
  }
  # const, no  trend, no seasD 
  if (state == 2){
    formulaExpression <- paste(formulaExpression, "1")
    critVal <- rbind( c(-3.42,-2.85,-2.55), c(-2.53,-1.93,-1.61), c(4.83,3.08,2.37), c(4.01,2.76,2.20), c(4.52,3.36,2.83))
  }
  # const, trend, no seasD 
  if (state == 3){
    formulaExpression <- paste(formulaExpression, "1 + trend")
    critVal <- rbind( c(-3.98,-3.40,-3.11), c(-2.53,-1.93,-1.61), c(4.76,3.05,2.35), c(3.96,2.74,2.18), c(5.44,4.19,3.59))
  }
  # const, seasD, trend
  if (state == 4){
    formulaExpression <- paste(formulaExpression, "1 + seasDum + trend")
    critVal <- rbind( c(-3.96,-3.39,-3.10), c(-3.41,-2.82,-2.53), c(8.79,6.55,5.48), c(7.62,5.93,5.09), c(7.93,6.31,5.55))
  }
  # const, no trend, seasD
  if (state == 5){
    formulaExpression <- paste(formulaExpression, "0 + seasDum")
    critVal <- rbind( c(-3.41,-2.84,-2.54), c(-3.41,-2.83,-2.53), c(8.79,6.57,5.52), c(7.63,5.95,5.09), c(7.07,5.56,4.86))
  }

  
  rightFrame <- data.frame(yright)
  names(rightFrame)  <- paste("X", 1:ncol(yright), sep="")

  
  formula <- as.formula(paste(formulaExpression, paste(lagNamesToAdd, collapse=" + "))) 

  est0 <- lm(formula, data = rightFrame) # for pi1=pi2=pi3=pi4=0
  est1 <- update(est0, . ~ . + X1) # for pi2=pi3=pi4=0
  est2 <- update(est1, . ~ . + X2) # for pi3=pi4=0 
  estBase <- update(est2, . ~ . + X3 + X4) # base model
  
  
  F34  <- anova(estBase, est2)$F[2]
  F234  <- anova(estBase, est1)$F[2]
  F1234  <- anova(estBase, est0)$F[2]

  sumEst <- summary(estBase)
  coeff <- cbind(sumEst$coeff[,1],sumEst$coeff[,3])

  sigma <- sumEst$sigma
  resid <- estBase$resid
  hegy <- c(F34,F234,F1234)
  
  return(list("T"=Tnew, "coeff"=coeff, "sigma"=sigma,"resid"=resid,"hegy"=hegy, "critVal"=critVal))
}
