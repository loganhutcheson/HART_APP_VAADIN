import { format } from "util";

beforeEach(() => {
  const { error } = global.console;

  global.console.error = (...args) => {
    for (let i = 0; i < args.length; i += 1) {
      const arg = args[i];
    
      // add patterns here that should fail a test
      if (typeof arg === "string" &&
           (arg.includes("Vue warn") || arg.includes("Not implemented"))) {
        throw new Error(format(...args));
      }
    }
    error(...args);
  };
});
