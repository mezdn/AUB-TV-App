using Microsoft.AspNetCore.Identity;
using Microsoft.Extensions.Configuration;
using System.Linq;
using System.Threading.Tasks;

namespace Backend.Data
{
    public class DataFactory
    {
        private readonly TVContext context;
        private readonly UserManager<IdentityUser> userManager;
        private readonly SignInManager<IdentityUser> signInManager;

        public static IConfiguration Configuration;


        public DataFactory(TVContext context,
            UserManager<IdentityUser> userManager,
            SignInManager<IdentityUser> signInManager,
            IConfiguration configuration)
        {
            this.context = context;
            this.userManager = userManager;
            this.signInManager = signInManager;
            Configuration = configuration;
        }

        public async Task SeedData()
        {
            var admin = await CreateUser("Admin");
        }
        
        public async Task<IdentityUser> CreateUser(string username)
        {
            var admin = new IdentityUser
            {
                UserName = username,
                NormalizedUserName = username.ToUpper(),
                EmailConfirmed = true,
            };

            var oldAdmin = from member in userManager.Users
                           where member.UserName == admin.UserName
                           select member;

            if (oldAdmin.FirstOrDefault() == null)
            {
                var adminPassword = Configuration.GetValue<string>("AdminPassword");
                var addAdmin = await userManager.CreateAsync(admin, adminPassword);
                if (addAdmin.Succeeded)
                {
                    await context.SaveChangesAsync();
                    await signInManager.SignInAsync(admin, isPersistent: false);
                }
                return admin;
            }

            return oldAdmin.FirstOrDefault();
        }
    }
}
