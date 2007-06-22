

# infocrits and a vector of optimal laglength 
# startlag, maxlag -- to search from  startlag  up to maxlag
# exlagFix -- lags of exogenous variables
# yr -- TxK matrix of endogenous variables
# x -- TxS matrix of exogenous variables
# d -- TxM matrix of deterministic variables

jm_infocrit <- function(y, x=NULL, d=NULL, startlag=0, maxlag=10, exlagFix=0){
    
    if (length(x)==0) exlagFix<-0
    
    max <- ifelse(exlagFix <= maxlag, maxlag, exlagFix)
    
    T <- nrow(y)
    K <- ncol(y)

    yLHS <- y[(max+1):T,]

    N <- T - max
    formulaExpression <- "yLHS ~ 0"
    
    if (length(d) > 0){
	d <- d[(max+1):T,]
	formulaExpression <- paste(formulaExpression, " + d")
    }
    if (length(x) > 0){
	x <- lagn(x, k=exlagFix, startLag=0)
	x <- x[(max+1):T,]
	formulaExpression <- paste(formulaExpression, " + x")
    }
    formulaExpression <- paste(formulaExpression, " + yRHS")
    infoCritFormula <- as.formula(formulaExpression)
    infoCritMat <- matrix(NA, nrow = maxlag-startlag+1, ncol = 4)
    for (i in startlag:maxlag){
	yRHS <- lagn(y, k=i, startLag=1)
	yRHS <- yRHS[(max+1):T,]
	est <- lm(infoCritFormula)
	sigma <- summary(est)$sigma
	numOfCoeff <- length(est$coeff)
	detSigma <- ifelse(K == 1, sigma, det(sigma))
	aic <- log(detSigma) + 2*i*K^2/N
	fpe <- ((N+numOfCoeff)/(N-numOfCoeff))^K*detSigma
	sc <- log(detSigma) + log(N)/N*i*K^2
	hq <- log(detSigma) + 2*log(log(N))/N*i*K^2
	infoCritMat[i - startlag + 1,] <- c(aic, fpe, sc, hq)
    }

    optLags <- max.col(-t(infoCritMat)) + startlag
    
    return(list("critMat"=infoCritMat, "optLags"=optLags));
}
