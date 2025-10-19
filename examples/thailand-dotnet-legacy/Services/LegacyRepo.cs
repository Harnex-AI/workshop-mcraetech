namespace ThailandLegacy;
public class LegacyRepo
{
    public string Save(string name, DateTime dob, string? address)
    {
        // pretend to persist and return an id
        return Guid.NewGuid().ToString();
    }
}

