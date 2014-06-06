//
//  ViewController.m
//  LinHongLing
//
//  Created by Golo on 13/9/8.
//  Copyright (c) 2013年 moskastudio. All rights reserved.
//

#import "HomeViewController.h"
#import "HomeCell.h"
#import "JSON.h"
#import "OneChannelViewController.h"

@interface HomeViewController ()
{
    NSString *linkType;
    NSMutableData *m_channelRequestData;
}
@end

@implementation HomeViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
    self.rotatingItemImageView = [[rotatingItem alloc] initWithFrame:CGRectMake(124, 237, 73, 73)];
    [self.view addSubview:self.rotatingItemImageView];
    [self.rotatingItemImageView startRotating];

    /*
    if (([[[UIDevice currentDevice] systemVersion] compare:@"7.0" options:NSNumericSearch] != NSOrderedAscending))
    {
        NSLog(@">= iOS 7.0");
        self.infoTableView.frame = CGRectMake(self.infoTableView.frame.origin.x, self.infoTableView.frame.origin.y-20, self.infoTableView.frame.size.width, self.infoTableView.frame.size.height + 20);
    }*/
    self.channelArray  = [[NSMutableArray alloc] init];
    [self loadChannelFromServer];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
}

- (void)viewWillAppear:(BOOL)animated
{
    // do version check for this app
}

- (void)loadChannelFromServer
{

    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    
    //load offline
    self.channelArray = [[NSMutableArray alloc] initWithArray:[defaults valueForKey:@"channelArray"]];
    if (!self.channelArray)
    {
        self.channelArray = [[NSMutableArray alloc] init];
    }
    [self.channelTableView reloadData];
    
    //load from server
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    NSURL *connection = [[NSURL alloc] initWithString:@"http://fakenews.tw/api/channel"];
    NSString *httpBodyString = @"";
    [request setURL:connection];
    [request setHTTPMethod:@"GET"];
    [request setHTTPBody:[httpBodyString dataUsingEncoding:NSUTF8StringEncoding]];
    linkType = @"channel";
    m_channelRequestData = [[NSMutableData alloc] init];
    [[[NSURLConnection alloc] initWithRequest:request delegate:self] start];
}

- (void)connection:(NSURLConnection *)connection didReceiveResponse:(NSURLResponse *)response
{
    NSLog(@"didReceiveResponse");
    [m_channelRequestData setLength:0];
}

- (void)connection:(NSURLConnection *)connection didReceiveData:(NSData *)data
{
    NSLog(@"didReceiveData,Data Size:%d",[data length]);
    [m_channelRequestData appendData:data];
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
    NSString * jsonString = [[NSString alloc] initWithData:m_channelRequestData encoding:NSUTF8StringEncoding];
    //NSLog(@"jsonString:%@", jsonString);
    id responses =  [jsonParser objectWithString:jsonString error:NULL];
    NSDictionary * feeds = (NSDictionary *)responses;
    self.channelArray = [feeds objectForKey:@"result"];
    //NSLog(@"self.channelArray:%@", self.channelArray);

    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults setValue:self.channelArray forKey:@"channelArray"];
    [defaults synchronize];

    [self.channelTableView reloadData];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    [self loadChannelFromServer];
}

#pragma mark - Table View

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [self.channelArray count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    HomeCell *cell = [tableView dequeueReusableCellWithIdentifier:@"homeCell"];
    cell.tag = indexPath.row;
    NSMutableDictionary *eachRow = [self.channelArray objectAtIndex:indexPath.row];
    cell.rankLable.text = [NSString stringWithFormat:@"%d",indexPath.row + 1];
    cell.nameLable.text = [eachRow valueForKey:@"channelName"];
    cell.numberLable.text = [NSString stringWithFormat:@"%@",[eachRow valueForKey:@"size"]];
    if (indexPath.row == 0)
    {
        cell.rankLable.hidden = YES;
        cell.rankBackgroundImageView.image = [UIImage imageNamed:@"FP_rank-no1-44x44"];
    }
    else if (indexPath.row < 5)
    {
        cell.rankLable.hidden = NO;
        cell.rankBackgroundImageView.image = [UIImage imageNamed:@"FP_rank-no-44x44"];
    }
    else
    {
        cell.rankLable.hidden = NO;
        cell.rankBackgroundImageView.image = [UIImage imageNamed:@"FP_rank-nogray-44x44"];
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

-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(HomeCell *)sender
{
    if ([[segue identifier] isEqualToString:@"showOneChannel"])
    {
        OneChannelViewController *vc = [segue destinationViewController];
        vc.selectedChannel = [self.channelArray objectAtIndex:sender.tag];
        vc.rank = sender.tag;
    }
}
@end
