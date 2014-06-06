//
//  OneCategoryViewController.m
//  fakenews
//
//  Created by Golo on 14/6/5.
//  Copyright (c) 2014年 moskastudio. All rights reserved.
//

#import "OneCategoryViewController.h"
#import "JSON.h"
#import "OneCategoryCell.h"
#import "OneComplainViewController.h"

@interface OneCategoryViewController ()
{
    NSString *linkType;
    NSMutableData *m_complainRequestData;
}
@end

@implementation OneCategoryViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    NSLog(@"selectedChannel:%@",self.selectedChannel);
    self.rankLable.text = [NSString stringWithFormat:@"%d",self.rank + 1];
    self.channelNameLable.text = [self.selectedChannel valueForKey:@"channelName"];
    self.channelNumberLable.text = [NSString stringWithFormat:@"%@",[self.selectedChannel valueForKey:@"size"]];
    if (self.rank == 0)
    {
        self.rankLable.hidden = YES;
        self.rankBackgroundImageView.image = [UIImage imageNamed:@"FP_rank-no1-44x44"];
    }
    else if (self.rank < 5)
    {
        self.rankLable.hidden = NO;
        self.rankBackgroundImageView.image = [UIImage imageNamed:@"FP_rank-no-44x44"];
    }
    else
    {
        self.rankLable.hidden = NO;
        self.rankBackgroundImageView.image = [UIImage imageNamed:@"FP_rank-nogray-44x44"];
    }
    self.categoryNameLable.text = [self.selectedCategory valueForKey:@"categoryName"];
    
    self.categoryNumberLable.text = [NSString stringWithFormat:@"%@",[self.selectedCategory valueForKey:@"size"]];
    
    UITapGestureRecognizer *tapLogo = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapLogo)];
    [self.logoImageView addGestureRecognizer:tapLogo];
    
    self.rotatingItemImageView = [[rotatingItem alloc] initWithFrame:CGRectMake(124, 237, 73, 73)];
    [self.view addSubview:self.rotatingItemImageView];
    [self.rotatingItemImageView startRotating];
    self.complainArray  = [[NSMutableArray alloc] init];
    [self loadComplainFromServer];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)tapLogo
{
    [self.navigationController popViewControllerAnimated:YES];
}

- (void)loadComplainFromServer
{
    
    
    //load offline
    
    /*
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];

    self.complainArray = [[NSMutableArray alloc] initWithArray:[defaults valueForKey:@"complainArray"]];
    if (!self.complainArray)
    {
        self.complainArray = [[NSMutableArray alloc] init];
    }
    [self.complainTableView reloadData];
    */
    //load from server
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    NSString *queryURLString = [NSString stringWithFormat:@"http://fakenews.tw/api/complain?channelName=%@&complainCategory=%@",[self.selectedChannel valueForKey:@"channelName"],[self.selectedCategory valueForKey:@"categoryName"]];
    queryURLString = [queryURLString stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    NSURL *connection = [[NSURL alloc] initWithString:queryURLString];
    NSString *httpBodyString = @"";
    [request setURL:connection];
    [request setHTTPMethod:@"GET"];
    [request setHTTPBody:[httpBodyString dataUsingEncoding:NSUTF8StringEncoding]];
    linkType = @"complain";
    m_complainRequestData = [[NSMutableData alloc] init];
    [[[NSURLConnection alloc] initWithRequest:request delegate:self] start];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
    NSLog(@"didReceiveResponse");
    [m_complainRequestData setLength:0];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    NSLog(@"didReceiveData,Data Size:%d",[data length]);
    [m_complainRequestData appendData:data];
}

- (void)connection:(NSURLConnection *)connection didFailWithError:(NSError *)error
{
    NSLog(@"connection didFailWithError:%@",error.description);
    [self.rotatingItemImageView stopRotating];
    UIAlertView *message = [[UIAlertView alloc] initWithTitle:@"Connection Problem"
                                                      message:@"Cannot connect to the server, please check your internet status."
                                                     delegate:self
                                            cancelButtonTitle:@"Reload"
                                            otherButtonTitles:nil];
    [message show];
}

- (void)connectionDidFinishLoading:(NSURLConnection *)connection
{
    NSLog(@"connectionDidFinishLoading");
    [self.rotatingItemImageView stopRotating];
    SBJSON *jsonParser = [SBJSON new];
    NSString * jsonString = [[NSString alloc] initWithData:m_complainRequestData encoding:NSUTF8StringEncoding];
    //NSLog(@"jsonString:%@", jsonString);
    id responses =  [jsonParser objectWithString:jsonString error:NULL];
    NSDictionary * feeds = (NSDictionary *)responses;
    self.complainArray = [feeds objectForKey:@"result"];
    //NSLog(@"self.channelArray:%@", self.channelArray);
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setValue:self.complainArray forKey:@"complainArray"];
    [defaults synchronize];
    
    [self.complainTableView reloadData];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    [self loadComplainFromServer];
}

#pragma mark - Table View

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.complainArray count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    OneCategoryCell *cell = [tableView dequeueReusableCellWithIdentifier:@"oneCategoryCell"];
    cell.tag = indexPath.row;
    NSMutableDictionary *eachRow = [self.complainArray objectAtIndex:indexPath.row];
    cell.cidLable.text = [NSString stringWithFormat:@"案號：%@",[eachRow valueForKey:@"cid"]];
    cell.titleLable.text = [eachRow valueForKey:@"complainTitle"];
    if ([[eachRow valueForKey:@"status"] isEqualToString:@"pending"])
    {
        cell.statusLable.text = @"處理中";
        cell.statusBackgroundImageView.backgroundColor = [UIColor colorWithRed:204.0/255.0 green:204.0/255.0 blue:204.0/255.0 alpha:1.0];
    }
    else
    {
        cell.statusLable.text = @"已結案";
        cell.statusBackgroundImageView.backgroundColor = [UIColor colorWithRed:95.0/255.0 green:227.0/255.0 blue:229.0/255.0 alpha:1.0];
    }
    
    return cell;
}


- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath
{
    // Return NO if you do not want the specified item to be editable.
    return NO;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath
{
    NSLog(@"editingStyle");
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        NSLog(@"UITableViewCellEditingStyleDelete");
        
        //[_objects removeObjectAtIndex:indexPath.row];
        //[tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        NSLog(@"UITableViewCellEditingStyleInsert");
        
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }
}

-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    //NSMutableDictionary *eachRow = [self.channelArray objectAtIndex:indexPath.row];
    
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(OneCategoryCell *)sender
{
    if ([[segue identifier] isEqualToString:@"showOneComplain"])
    {
        OneComplainViewController *vc = [segue destinationViewController];
        vc.selectedComplain = [self.complainArray objectAtIndex:sender.tag];
    }
}

@end
