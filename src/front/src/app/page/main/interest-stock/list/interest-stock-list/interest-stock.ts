export interface InterestStock {
    sector: string;
    ticker: string;
    currentPrice: number;
    desiredPurchasePrice: number;
    declineRate: number;
    under10Price: string;
    w52: string;
    dividendYield: number;
    fiveyearAvgDividendYield: number;
    payoutRatio: number;
    dividendGrowth: number;
    annualPayout: number;
    dividendPayMonth: string;
}

