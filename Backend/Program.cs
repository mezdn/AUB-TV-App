using System;
using Backend.Data;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;

namespace Backend
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var host = CreateHostBuilder(args).Build();

            using (var scope = host.Services.CreateScope())
            {
                var services = scope.ServiceProvider;

                var context = services.GetService<TVContext>();
                var userManager = services.GetService<UserManager<IdentityUser>>();
                var signInManager = services.GetService<SignInManager<IdentityUser>>();
                var configuration = services.GetService<IConfiguration>();

                try
                {
                    var dataFactory = new DataFactory(context, userManager, signInManager, configuration);
                    dataFactory.SeedData().Wait();
                }
                catch (Exception ex)
                {
                    var logger = services.GetRequiredService<ILogger<Program>>();
                    logger.LogError(ex, "An error occurred seeding the DB.");
                }
            }

            host.Run();
        }

        public static IHostBuilder CreateHostBuilder(string[] args) =>
            Host.CreateDefaultBuilder(args)
                .ConfigureWebHostDefaults(webBuilder =>
                {
                    webBuilder.UseSetting(WebHostDefaults.DetailedErrorsKey, "true");
                    webBuilder.CaptureStartupErrors(true);
                    webBuilder.UseStartup<Startup>();
                });
    }
}
