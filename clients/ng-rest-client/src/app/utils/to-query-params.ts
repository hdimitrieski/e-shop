
export const toQueryParams = (params: Record<string, number | string>): Record<string, string> => {
  const queryParams = {};

  Object.keys(params)
    .filter(paramName => params[paramName])
    .forEach(paramName => {
      queryParams[paramName] = params[paramName].toString();
    });

  return queryParams;
};
