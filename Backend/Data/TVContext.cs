using Backend.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace Backend.Data
{
    public class TVContext : IdentityDbContext<IdentityUser>
    {
        public TVContext(DbContextOptions<TVContext> options) :base(options)
        {
        }

        public DbSet<DisplayObject> DisplayObject { get; set; }
        public DbSet<Category> Category { get; set; }
        public DbSet<Event> Event { get; set; }
    }
}
